package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DietListResponse {
    private String name;
    private String clientEmail;
    private String description;
    private String morningMenu;
    private String lunchMenu;
    private String dinnerMenu;
    private String forbiddens;
    private int duration;
    private String dietTypeName;
    private String clientName;
    private String dietitianName;
    private LocalDateTime createdAt;
}
