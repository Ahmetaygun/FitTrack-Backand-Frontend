package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_info_id", nullable = false)
    private HealthInfo healthInfo;

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

    @Column(nullable = false)
    private String dietTypeName; // ✅ Eklenen alan

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
