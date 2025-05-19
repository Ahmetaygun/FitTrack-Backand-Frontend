package org.example.repository;

import org.example.model.Client;
import org.example.model.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

    //Belirli bir danışana ait tüm takip kayıtlarını getir
    List<Tracking> findAllByClient(Client client);

    //Belirli bir danışana ait takip kayıtlarını tarihe göre sıralı getir
    List<Tracking> findAllByClientOrderByCreatedAtDesc(Client client);

    List<Tracking> findAllByClientIn(List<Client> clients);


    //En son oluşturulan takip kaydını getir
    Tracking findTopByClientOrderByCreatedAtDesc(Client client);
}
