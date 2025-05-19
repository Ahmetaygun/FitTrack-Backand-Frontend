package org.example.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DietListRequest {
    private Long clientId;
    private String name;
    private String description;
    private String morningMenu;
    private String lunchMenu;
    private String dinnerMenu;
    private String forbiddens;
    private int duration;
}
