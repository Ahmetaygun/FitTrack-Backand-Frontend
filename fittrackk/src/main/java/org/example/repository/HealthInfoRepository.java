package org.example.repository;

import org.example.model.Client;
import org.example.model.HealthInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthInfoRepository extends JpaRepository<HealthInfo, Long> {
    // Sağlık bilgilerini ID ile almak
    Optional<HealthInfo> findById(Long id);

    // Sağlık bilgilerini client ile almak
    Optional<HealthInfo> findByClient(Client client);
}
