package de.kreuzwerker.cdc.messagingapp;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import org.assertj.core.groups.Tuple;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = "user-service.base-url:http://localhost:${RANDOM_PORT}",
    classes = UserServiceClient.class)
public class UserServiceGenericStateWithParameterContractTest {

    private static final String NAME = "user name for CDC";
    private static final LocalDateTime LAST_LOGIN = LocalDateTime.of(2018, 10, 16, 12, 34, 12);

    @ClassRule
    public static RandomPortRule randomPort = new RandomPortRule();

    @Rule
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2("user-service", null,
        randomPort.getPort(), this);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private UserServiceClient userServiceClient;


    @Pact(consumer = "messaging-app")
    public RequestResponsePact pactUserExists(PactDslWithProvider builder) {

        // See https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit#dsl-matching-methods
        DslPart body = LambdaDsl.newJsonBody((o) -> o
            .stringType("id", "1")
            .stringType("name", NAME)
            .timestamp("lastLogin", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Date.from(LAST_LOGIN.atZone(ZoneId.systemDefault()).toInstant()))
            .stringMatcher("role", "ADMIN|USER", "ADMIN")
            .minArrayLike("friends", 0, 2, friend -> friend
                .stringType("id", "2")
                .stringType("name", "a friend")
            )).build();

        return builder.given("default", Collections.singletonMap("userExists", true))
            .uponReceiving("A request for an existing user")
            .path("/users/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(body)
            .toPact();

    }

    @Pact(consumer = "messaging-app")
    public RequestResponsePact pactUserDoesNotExist(PactDslWithProvider builder) {

        return builder.given("default", Collections.singletonMap("userExists", false))
            .uponReceiving("A request for a non-existing user")
            .path("/users/1")
            .method("GET")
            .willRespondWith()
            .status(404)
            .toPact();
    }

    @PactVerification(fragment = "pactUserExists")
    @Test
    public void userExists() {
        final User user = userServiceClient.getUser("1");

        assertThat(user.getName()).isEqualTo(NAME);
        assertThat(user.getLastLogin()).isEqualTo(LAST_LOGIN);
        assertThat(user.getRole()).isEqualTo("ADMIN");
        assertThat(user.getFriends()).hasSize(2)
            // currently not possible to define multiple values, s. https://github.com/DiUS/pact-jvm/issues/379
            .extracting(Friend::getId, Friend::getName)
            .containsExactly(Tuple.tuple("2", "a friend"), Tuple.tuple("2", "a friend"));
    }

    @PactVerification(fragment = "pactUserDoesNotExist")
    @Test
    public void userDoesNotExist() {
        expectedException.expect(HttpClientErrorException.class);
        expectedException.expectMessage("404 Not Found");

        userServiceClient.getUser("1");
    }
}