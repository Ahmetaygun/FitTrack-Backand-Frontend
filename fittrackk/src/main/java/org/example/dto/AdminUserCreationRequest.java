package org.example.dto;

import lombok.Data;

@Data
public class AdminUserCreationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private String userType;         // "CLIENT" ya da "DIETITIAN"
    private String dietTypeName;     // CLIENT ise gerekir, DIETITIAN için null olabilir
}
