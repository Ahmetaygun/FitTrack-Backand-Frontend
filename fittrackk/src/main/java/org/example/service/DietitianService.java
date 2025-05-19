package org.example.service;

import org.example.dto.ClientDto;
import org.example.dto.DietListResponse;
import org.example.dto.DietitianDto;
import org.example.model.Client;
import org.example.model.DietList;
import org.example.model.Dietitian;
import org.example.model.HealthInfo;
import org.example.repository.ClientRepository;
import org.example.repository.DietListRepository;
import org.example.repository.DietitianRepository;
import org.example.repository.HealthInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietitianService {

    private final DietitianRepository dietitianRepository;
    private final ClientRepository clientRepository;
    private final HealthInfoRepository healthInfoRepository;
    private final DietListRepository dietListRepository;
    private final JwtService jwtService;

    @Autowired
    public DietitianService(DietitianRepository dietitianRepository,
                            ClientRepository clientRepository,
                            HealthInfoRepository healthInfoRepository,
                            DietListRepository dietListRepository,
                            JwtService jwtService) {
        this.dietitianRepository = dietitianRepository;
        this.clientRepository = clientRepository;
        this.healthInfoRepository = healthInfoRepository;
        this.dietListRepository = dietListRepository;
        this.jwtService = jwtService;
    }

    // Diyetisyen Kaydı
    public Dietitian registerDietitian(Dietitian dietitian) {
        return dietitianRepository.save(dietitian);
    }
    public List<ClientDto> getPatientsByDietitianId(Long dietitianId) {
        return clientRepository.findByDietitianId(dietitianId)
                .stream()
                .map(client -> new ClientDto(client.getFirstName(), client.getLastName(), client.getEmail()))
                .collect(Collectors.toList());
    }

    // Tüm Diyetisyenleri Listeleme
    public List<DietitianDto> getAllDietitians() {
        return dietitianRepository.findAll().stream()
                .map(d -> new DietitianDto(
                        d.getId(),
                        d.getFirstName(),
                        d.getLastName(),
                        d.getEmail(),
                        d.getRole().name()
                ))
                .toList();
    }

    // ID'ye göre Diyetisyen Getirme
    public Dietitian getDietitianById(Long id) {
        return dietitianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı!"));
    }

    // Diyetisyen Silme
    public void deleteDietitian(Long id) {
        dietitianRepository.deleteById(id);
    }

    // Diyetisyene Atanmış Tüm Hastaları DTO Olarak Listele
    public List<ClientDto> getAssignedClients(String token) {
        String email = jwtService.extractUsername(token);

        Dietitian dietitian = dietitianRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı!"));

        List<Client> clients = clientRepository.findByDietitian(dietitian);

        return clients.stream().map(client -> {

            ClientDto dto = new ClientDto();
            dto.setId(client.getId());
            dto.setFirstName(client.getFirstName());
            dto.setLastName(client.getLastName());
            dto.setEmail(client.getEmail());
            dto.setAge(client.getAge());
            dto.setDietTypeName(client.getDietType() != null ? client.getDietType().getName() : "Belirtilmemiş");

            HealthInfo info = healthInfoRepository.findByClient(client).orElse(null);
            if (info != null) {
                dto.setBloodPressure(info.getBloodPressure());
                dto.setBloodSugar(info.getBloodSugar());
                dto.setCholesterol(info.getCholesterol());
                dto.setAllergies(info.getAllergies());
                dto.setMedications(info.getMedications());
                dto.setOtherHealthConditions(info.getOtherHealthConditions());
            }

            return dto;
        }).toList();
    }

    // ✅ Diyetisyen İçin Diyet Listelerini Getir
    public List<DietListResponse> getDietLists(String token) {
        String email = jwtService.extractUsername(token);

        Dietitian dietitian = dietitianRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı!"));

        List<DietList> dietLists = dietListRepository.findByDietitianId(dietitian.getId());

        return dietLists.stream().map(d -> {
            DietListResponse response = new DietListResponse();
            response.setName(d.getName());
            response.setDescription(d.getDescription());
            response.setMorningMenu(d.getMorningMenu());
            response.setLunchMenu(d.getLunchMenu());
            response.setDinnerMenu(d.getDinnerMenu());
            response.setForbiddens(d.getForbiddens());
            response.setDuration(d.getDuration());
            response.setDietTypeName(d.getDietType() != null ? d.getDietType().getName() : "Belirtilmemiş");
            response.setClientName(d.getClient() != null ?
                    d.getClient().getFirstName() + " " + d.getClient().getLastName() : "Belirtilmemiş");
            response.setDietitianName(dietitian.getFirstName() + " " + dietitian.getLastName());
            response.setCreatedAt(d.getCreatedAt());
            return response;
        }).toList();
    }
}
