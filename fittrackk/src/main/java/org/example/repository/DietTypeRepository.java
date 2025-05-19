package org.example.repository;

import org.example.model.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DietTypeRepository extends JpaRepository<DietType, Long> {
    // Diyet tipi adı ile sorgu yapmak için
    Optional<DietType> findByName(String name);
    Optional<DietType> findById(Long id);

}
