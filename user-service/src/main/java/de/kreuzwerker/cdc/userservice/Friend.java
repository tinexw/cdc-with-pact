package de.kreuzwerker.cdc.userservice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friend {

    private String id;
    private String name;

}
