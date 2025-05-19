package org.example.service;

import lombok.RequiredArgsConstructor;

import org.example.dto.ClientSelfDto;
import org.example.model.*;
import org.example.repository.ClientRepository;
import org.example.repository.DietTypeRepository;
import org.example.repository.DietitianRepository;
import org.example.repository.HealthInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final DietTypeRepository dietTypeRepository;
    private final DietitianRepository dietitianRepository;
    private final HealthInfoRepository healthInfoRepository;
    private final TrackingService trackingService;
    private final JwtService jwtService;

    // Danışan kaydetme
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    //Danışanları listeleme
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    //Danışan bilgilerini güncelleme
    public Client updateClient(Long clientId, Client clientDetails) {
        return clientRepository.findById(clientId).map(existing -> {
            existing.setFirstName(clientDetails.getFirstName());
            existing.setEmail(clientDetails.getEmail());
            return clientRepository.save(existing);
        }).orElse(null);
    }

    //Danışan ID ile getirme
    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    //E-posta ile danışan getirme
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    //Diyet tipi seçimi
    public void assignDietType(String token, Long dietTypeId) {
        String email = jwtService.extractUsername(token);
        Client client = clientRepository.findByEmail(email);

        DietType dietType = dietTypeRepository.findById(dietTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Diyet tipi bulunamadı."));

        client.setDietType(dietType);
        clientRepository.save(client);
    }

    //Diyetisyen seçimi – sadece sağlık bilgileri ve diyet tipi varsa
    public void assignDietitian(String token, Long dietitianId) {
        String email = jwtService.extractUsername(token);
        Client client = clientRepository.findByEmail(email);

        if (client.getDietType() == null) {
            throw new IllegalStateException("Lütfen önce diyet tipi seçiniz.");
        }

        HealthInfo healthInfo = healthInfoRepository.findByClient(client)
                .orElseThrow(() -> new IllegalStateException("Lütfen önce sağlık bilgilerinizi giriniz."));

        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new IllegalArgumentException("Diyetisyen bulunamadı."));

        client.setDietitian(dietitian);
        clientRepository.save(client);

        // Tracking kaydı oluştur
        trackingService.createTrackingRecord(client, healthInfo);
    }

    //Danışan silme
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    //Token'dan email çıkar ve Client getir
    public Client getClientByToken(String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));
        return clientRepository.findByEmail(email);
    }

    //Token'dan sadece email çıkar
    public String extractEmailFromToken(String token) {
        return jwtService.extractUsername(token.replace("Bearer ", ""));
    }

  public ClientSelfDto getSelfInfo(String email) {
    Client client = getClientByEmail(email);

    String dietitianName = client.getDietitian() != null
        ? client.getDietitian().getFirstName() + " " + client.getDietitian().getLastName()
        : null;

    String dietTypeName = client.getDietType() != null
        ? client.getDietType().getName()
        : null;

    return ClientSelfDto.builder()
            .id(client.getId())
            .email(client.getEmail())
            .firstName(client.getFirstName())
            .lastName(client.getLastName())
            .age(client.getAge())
            .gender(client.getGender())
            .dietitianName(dietitianName)
            .dietTypeName(dietTypeName)
            .build();
}


}
