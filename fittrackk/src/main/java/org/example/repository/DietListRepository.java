package org.example.repository;

import org.example.model.Client;
import org.example.model.DietList;
import org.example.model.DietType;
import org.example.model.Dietitian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietListRepository extends JpaRepository<DietList, Long> {

    //Belirli danışana ait tüm diyet listelerini getir
    List<DietList> findAllByClient(Client client);
    //Belirli diyet tipine ait tüm diyet listelerini getir
    List<DietList> findAllByDietType(DietType dietType);
    List<DietList> findByDietitianId(Long dietitianId);

    //Belirli diyetisyenin oluşturduğu tüm listeleri getir
    List<DietList> findAllByDietitian(Dietitian dietitian);

    //Danışanın en son oluşturulmuş diyet listesini getir
    Optional<DietList> findTopByClientOrderByCreatedAtDesc(Client client);
}
