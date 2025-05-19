package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.TrackingResponseDto;
import org.example.model.*;
import org.example.repository.ClientRepository;
import org.example.repository.DietitianRepository;
import org.example.repository.TrackingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final ClientRepository clientRepository;
    private final DietitianRepository dietitianRepository;
    private final JwtService jwtService;

    public void createTrackingRecord(Client client, HealthInfo healthInfo) {
        if (client == null || healthInfo == null || client.getDietType() == null) {
            throw new IllegalArgumentException("Takip oluşturulamadı: Bilgiler eksik.");
        }

        Tracking tracking = Tracking.builder()
                .client(client)
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .age(client.getAge())
                .gender(client.getGender())
                .healthInfo(healthInfo)
                .bloodPressure(healthInfo.getBloodPressure())
                .bloodSugar(healthInfo.getBloodSugar())
                .cholesterol(healthInfo.getCholesterol())
                .allergies(healthInfo.getAllergies())
                .medications(healthInfo.getMedications())
                .otherHealthConditions(healthInfo.getOtherHealthConditions())
                .dietTypeName(client.getDietType().getName())
                .createdAt(LocalDateTime.now())
                .build();

        trackingRepository.save(tracking);
    }

    public List<TrackingResponseDto> getTrackingDtosByClient(Client client) {
        return trackingRepository.findAllByClientOrderByCreatedAtDesc(client)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TrackingResponseDto> getAllTrackingDtos() {
        return trackingRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<TrackingResponseDto> getTrackingDtosForCurrentDietitian(String token) {
        String email = jwtService.extractUsername(token);
        Dietitian dietitian = dietitianRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı."));

        // Diyetisyene bağlı danışanlar
        List<Client> clients = clientRepository.findByDietitian(dietitian);

        // O danışanların takip kayıtları
        List<Tracking> trackings = trackingRepository.findAllByClientIn(clients);

        return trackings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    private TrackingResponseDto mapToDto(Tracking tracking) {
        TrackingResponseDto dto = new TrackingResponseDto();
        dto.setFirstName(tracking.getFirstName());
        dto.setLastName(tracking.getLastName());
        dto.setAge(tracking.getAge());
        dto.setGender(tracking.getGender());
        dto.setBloodPressure(tracking.getBloodPressure());
        dto.setBloodSugar(tracking.getBloodSugar());
        dto.setCholesterol(tracking.getCholesterol());
        dto.setAllergies(tracking.getAllergies());
        dto.setMedications(tracking.getMedications());
        dto.setOtherHealthConditions(tracking.getOtherHealthConditions());
        dto.setDietTypeName(tracking.getDietTypeName());
        dto.setCreatedAt(tracking.getCreatedAt());

        // ✅ Eksik olan kısımlar eklendi
        if (tracking.getClient() != null) {
            dto.setEmail(tracking.getClient().getEmail());
        }
        return dto;
    }

}
