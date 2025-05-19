package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "diet_lists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name; // Diyetin adı

    @Column(nullable = false)
    private String description; // Diyet hakkında açıklama

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dietitian_id", nullable = false)
    private Dietitian dietitian; // Diyeti veren diyetisyen

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client; // Diyet listesine sahip olan danışan

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_type_id", nullable = false)
    private DietType dietType; // Diyet tipi

    @Column(nullable = false)
    private String morningMenu;

    @Column(nullable = false)
    private String lunchMenu;

    @Column(nullable = false)
    private String dinnerMenu;


    @Column(nullable = false)
    private String forbiddens;  // Yasaklar

    @Column(nullable = false)
    private LocalDateTime createdAt; // Diyetin oluşturulma tarihi

    @Column(nullable = false)
    private int duration; // Diyetin süresi (gün veya hafta)
}
