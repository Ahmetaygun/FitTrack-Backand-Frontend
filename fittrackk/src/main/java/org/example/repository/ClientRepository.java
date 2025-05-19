package org.example.repository;

import org.example.model.Client;
import org.example.model.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    //E-posta ile danışan getirme (JWT için kullanılır)
    Client findByEmail(String email);
    List<Client> findByDietitianId(Long dietitianId);
    //ID ile danışan getirme (zaten JpaRepository'de var ama burada da kalabilir)
    Optional<Client> findById(Long id);

    //Belirli diyetisyene atanmış tüm danışanları getir
    List<Client> findByDietitian(Dietitian dietitian);

    // İsteğe bağlı: aktif olan tüm danışanları getir
    List<Client> findByIsActiveTrue();
}
