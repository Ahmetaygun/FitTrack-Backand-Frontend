package org.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "health_infos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false, unique = true)
    private Client client;

    @Column(nullable = false)
    private String bloodPressure;

    @Column(nullable = false)
    private String bloodSugar;

    @Column(nullable = false)
    private String cholesterol;

    @Column(nullable = false)
    private String allergies;

    @Column(nullable = false)
    private String medications;

    @Column(nullable = false)
    private String otherHealthConditions;
}
