package org.example.dto;

import lombok.Data;

@Data
public class HealthInfoRequest {
    private String bloodPressure;
    private String bloodSugar;
    private String cholesterol;
    private String allergies;
    private String medications;
    private String otherHealthConditions;
}
