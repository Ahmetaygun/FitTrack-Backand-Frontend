package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dto.HealthInfoRequest;
import org.example.model.Client;
import org.example.model.HealthInfo;
import org.example.repository.ClientRepository;
import org.example.repository.HealthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HealthInfoService {

    private final HealthInfoRepository healthInfoRepository;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;

    @Autowired
    public HealthInfoService(HealthInfoRepository healthInfoRepository,
                             ClientRepository clientRepository,
                             JwtService jwtService) {
        this.healthInfoRepository = healthInfoRepository;
        this.clientRepository = clientRepository;
        this.jwtService = jwtService;
    }

    //Orijinal metot: Sağlık bilgisi kaydetme
    public HealthInfo saveHealthInfo(HealthInfo healthInfo) {
        return healthInfoRepository.save(healthInfo);
    }

    //Orijinal metot: Tüm sağlık bilgilerini listeleme
    public List<HealthInfo> getAllHealthInfos() {
        return healthInfoRepository.findAll();
    }

    //Orijinal metot: ID ile sağlık bilgisi getirme
    public Optional<HealthInfo> getHealthInfoById(Long healthInfoId) {
        return healthInfoRepository.findById(healthInfoId);
    }

    //Yeni: Token ile sağlık bilgisi görüntüleme
   public HealthInfoRequest getHealthInfoByToken(String token) {
    String email = jwtService.extractUsername(token);
    Client client = clientRepository.findByEmail(email);
    if (client == null) {
        return null;
    }

    HealthInfo info = healthInfoRepository.findByClient(client).orElse(null);
    if (info == null) {
        return null;
    }

    HealthInfoRequest dto = new HealthInfoRequest();
    dto.setBloodPressure(info.getBloodPressure());
    dto.setBloodSugar(info.getBloodSugar());
    dto.setCholesterol(info.getCholesterol());
    dto.setAllergies(info.getAllergies());
    dto.setMedications(info.getMedications());
    dto.setOtherHealthConditions(info.getOtherHealthConditions());

    return dto;
}


    //Yeni: Token ile sağlık bilgisi oluştur/güncelle
    @Transactional
    public void saveOrUpdateHealthInfo(String token, HealthInfoRequest request) {
        String email = jwtService.extractUsername(token);
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            throw new IllegalArgumentException("Kullanıcı bulunamadı.");
        }

        HealthInfo info = healthInfoRepository.findByClient(client).orElse(new HealthInfo());
        info.setClient(client);
        info.setBloodPressure(request.getBloodPressure());
        info.setBloodSugar(request.getBloodSugar());
        info.setCholesterol(request.getCholesterol());
        info.setAllergies(request.getAllergies());
        info.setMedications(request.getMedications());
        info.setOtherHealthConditions(request.getOtherHealthConditions());

        healthInfoRepository.save(info);
    }
}
