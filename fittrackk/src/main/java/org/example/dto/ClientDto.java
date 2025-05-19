package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClientDto {
    private Long id;
    private Integer age;
    private String firstName;
    private String lastName;
    private String email;
    private String dietTypeName;
    private String bloodPressure;
    private String bloodSugar;
    private String cholesterol;
    private String allergies;
    private String medications;
    private String otherHealthConditions;
    public ClientDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
