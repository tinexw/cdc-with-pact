package de.kreuzwerker.cdc.userservice;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class User {

    private String id;
    private String legacyId;
    private String name;
    private UserRole role;
    private Date lastLogin;
    @Singular
    private List<Friend> friends;


}
