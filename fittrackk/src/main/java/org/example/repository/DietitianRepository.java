package org.example.repository;

import org.example.model.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietitianRepository extends JpaRepository<Dietitian, Long> {

    // E-posta ile diyetisyen bul (JWT için kullanılıyor)
    Optional<Dietitian> findByEmail(String email);

    // ID ile diyetisyen bul (gereksiz ama kalabilir, JpaRepository'den gelir)
    Optional<Dietitian> findById(Long id);

    // Diyetisyenin ad ve soyadına göre arama (opsiyonel)
    List<Dietitian> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

}
