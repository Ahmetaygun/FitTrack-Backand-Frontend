package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingResponseDto {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String bloodPressure;
    private String bloodSugar;
    private String cholesterol;
    private String allergies;
    private String medications;
    private String otherHealthConditions;
    private String dietTypeName;
    private LocalDateTime createdAt;
    private String email;

}
