package de.kreuzwerker.cdc.userservice;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Provider("user-service")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//pact_broker is the service name in docker-compose
@PactBroker(host = "pact_broker", tags = "${pactbroker.tags:prod}")
public class GenericStateWithParameterContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @MockBean
    private UserService userService;

    @State("default")
    public void toDefaultState(Map<String, Object> params) {
        final boolean userExists = (boolean) params.get("userExists");
        if (userExists) {
            when(userService.findUser(any())).thenReturn(User.builder()
                .id("1")
                .legacyId(UUID.randomUUID().toString())
                .name("Beth")
                .role(UserRole.ADMIN)
                .lastLogin(new Date())
                .friend(Friend.builder().id("2").name("Ronald Smith").build())
                .friend(Friend.builder().id("3").name("Matt Spencer").build())
                .build());
        } else {
            when(userService.findUser(any())).thenThrow(NotFoundException.class);
        }
    }


}

