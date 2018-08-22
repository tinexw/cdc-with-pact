package de.kreuzwerker.cdc.messagingapp;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String name;
    private String role;
    private LocalDateTime lastLogin;
    private List<Friend> friends;


}
