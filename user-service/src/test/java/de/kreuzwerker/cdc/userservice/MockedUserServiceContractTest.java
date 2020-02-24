package de.kreuzwerker.cdc.userservice;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Provider("user-service")
@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
public class MockedUserServiceContractTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @MockBean
    private UserService userService;

    @State("User 1 exists")
    public void user1Exists() {
        when(userService.findUser(any())).thenReturn(User.builder()
            .id("1")
            .legacyId(UUID.randomUUID().toString())
            .name("Beth")
            .role(UserRole.ADMIN)
            .lastLogin(new Date())
            .friend(Friend.builder().id("2").name("Ronald Smith").build())
            .friend(Friend.builder().id("3").name("Matt Spencer").build())
            .build());
    }

    @State("User 2 does not exist")
    public void user2DoesNotExist() {
        when(userService.findUser(any())).thenThrow(NotFoundException.class);
    }

}

