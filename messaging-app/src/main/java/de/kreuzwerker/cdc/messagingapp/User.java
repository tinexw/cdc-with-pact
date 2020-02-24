package de.kreuzwerker.cdc.messagingapp;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {

    private String name;
    private String role;
    private LocalDateTime lastLogin;
    private List<Friend> friends;


}
