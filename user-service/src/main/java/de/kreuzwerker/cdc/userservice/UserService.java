package de.kreuzwerker.cdc.userservice;

import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User findUser(String userId) {
        return User.builder()
            .id(userId)
            .legacyId(UUID.randomUUID().toString())
            .name("Beth")
            .role(UserRole.ADMIN)
            .lastLogin(new Date())
            .friend(Friend.builder().id("2").name("Ronald Smith").build())
            .friend(Friend.builder().id("3").name("Matt Spencer").build())
            .build();
    }
}
