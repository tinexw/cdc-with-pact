package de.kreuzwerker.cdc.messagingapp;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "user-service.base-url:http://localhost:8080",
    classes = UserServiceClient.class)
@Disabled
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "user-service", port = "8080")
public class UserServiceContractTestV1 {

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

    @PactTestFor(pactMethod = "pactUserExists")
    @Test
    public void userExists() {
        User user = userServiceClient.getUser("1");

        assertThat(user.getName()).isEqualTo("user name for CDC");
    }
}