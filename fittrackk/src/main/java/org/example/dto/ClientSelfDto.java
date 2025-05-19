package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientSelfDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String dietitianName;     
    private String dietTypeName;  
}
