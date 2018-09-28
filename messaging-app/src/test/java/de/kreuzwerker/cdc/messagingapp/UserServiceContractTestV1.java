package de.kreuzwerker.cdc.messagingapp;

import static org.assertj.core.api.Assertions.assertThat;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = "user-service.base-url:http://localhost:${RANDOM_PORT}",
    classes = UserServiceClient.class)
@Ignore
public class UserServiceContractTestV1 {

    @ClassRule
    public static RandomPortRule randomPort = new RandomPortRule();

    @Rule
    public PactProviderRuleMk2 provider = new PactProviderRuleMk2("user-service", null,
        randomPort.getPort(), this);

    @Autowired
    private UserServiceClient userServiceClient;


    @Pact(consumer = "messaging-app")
    public RequestResponsePact pactUserExists(PactDslWithProvider builder) {
        return builder.given(
            "User 1 exists")
            .uponReceiving("A request to /users/1")
            .path("/users/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(LambdaDsl.newJsonBody((o) ->
                o.stringType("name", "user name for CDC")
            ).build()).toPact();
    }

    @PactVerification(fragment = "pactUserExists")
    @Test
    public void userExists() {
        User user = userServiceClient.getUser("1");

        assertThat(user.getName()).isEqualTo("user name for CDC");
    }
}