package org.example.dto;

import lombok.Data;

@Data
public class CreateDietitianRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
