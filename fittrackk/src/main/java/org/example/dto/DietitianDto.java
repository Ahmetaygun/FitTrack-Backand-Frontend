package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietitianDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
