package org.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diet_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name; // Diyet tipinin adı

    @Column(nullable = true)
    private String description; // Diyet tipi hakkında açıklama (isteğe bağlı)
}
