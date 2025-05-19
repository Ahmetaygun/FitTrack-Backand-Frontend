package org.example.dto;

import lombok.Data;

@Data
public class CreateClientRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private Long dietTypeId;
}
