package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.DietListRequest;
import org.example.dto.DietListResponse;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietListService {

    private final DietListRepository dietListRepository;
    private final DietitianRepository dietitianRepository;
    private final ClientRepository clientRepository;
    private final DietTypeRepository dietTypeRepository;
    private final JwtService jwtService;

    //Diyet listesi oluşturma (diyetisyen tarafından)
    public void createDietList(String token, DietListRequest request) {
        String email = jwtService.extractUsername(token);
        Dietitian dietitian = dietitianRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Danışan bulunamadı"));

        DietList list = DietList.builder()
                .name(request.getName())
                .description(request.getDescription())
                .morningMenu(request.getMorningMenu())
                .lunchMenu(request.getLunchMenu())
                .dinnerMenu(request.getDinnerMenu())
                .forbiddens(request.getForbiddens())
                .duration(request.getDuration())
                .createdAt(LocalDateTime.now())
                .dietitian(dietitian)
                .client(client)
                .dietType(client.getDietType())
                .build();

        dietListRepository.save(list);
    }

    //Giriş yapan danışan kendi diyet listesini görüntüler
    public DietListResponse getClientDietList(String token) {
        String email = jwtService.extractUsername(token);
        Client client = clientRepository.findByEmail(email);

        DietList list = dietListRepository.findTopByClientOrderByCreatedAtDesc(client)
                .orElseThrow(() -> new RuntimeException("Diyet listesi bulunamadı"));

        DietListResponse response = new DietListResponse();
        response.setName(list.getName());
        response.setDescription(list.getDescription());
        response.setMorningMenu(list.getMorningMenu());
        response.setLunchMenu(list.getLunchMenu());
        response.setDinnerMenu(list.getDinnerMenu());
        response.setForbiddens(list.getForbiddens());
        response.setDuration(list.getDuration());
        response.setCreatedAt(list.getCreatedAt());
        response.setDietTypeName(list.getDietType().getName());
        response.setClientName(client.getFirstName() + " " + client.getLastName());
        response.setDietitianName(list.getDietitian().getFirstName() + " " + list.getDietitian().getLastName());

        return response;
    }

    //Danışan ID’sine göre tüm diyet listelerini getir (Admin/Diyetisyen)
    public List<DietList> getDietListsByClientId(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Danışan bulunamadı"));
        return dietListRepository.findAllByClient(client);
    }

    //Diyet Tipi ID’sine göre tüm diyet listelerini getir (Admin/Diyetisyen)
    public List<DietList> getDietListsByDietTypeId(Long dietTypeId) {
        DietType dietType = dietTypeRepository.findById(dietTypeId)
                .orElseThrow(() -> new RuntimeException("Diyet tipi bulunamadı"));
        return dietListRepository.findAllByDietType(dietType);
    }

    //Diyetisyen ID’sine göre tüm diyet listelerini getir (Admin)
    public List<DietList> getDietListsByDietitianId(Long dietitianId) {
        Dietitian dietitian = dietitianRepository.findById(dietitianId)
                .orElseThrow(() -> new RuntimeException("Diyetisyen bulunamadı"));
        return dietListRepository.findAllByDietitian(dietitian);
    }

    //Diyet listesi güncelleme (diyetisyen)
    public DietList updateDietList(Long id, DietList updatedList) {
        DietList existing = dietListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diyet listesi bulunamadı"));

        existing.setName(updatedList.getName());
        existing.setDescription(updatedList.getDescription());
        existing.setMorningMenu(updatedList.getMorningMenu());
        existing.setLunchMenu(updatedList.getLunchMenu());
        existing.setDinnerMenu(updatedList.getDinnerMenu());
        existing.setForbiddens(updatedList.getForbiddens());
        existing.setDuration(updatedList.getDuration());

        return dietListRepository.save(existing);
    }

    //Diyet listesi oluşturma (admin panelinden direkt kullanılabilir)
    public DietList createDietList(DietList dietList) {
        dietList.setCreatedAt(LocalDateTime.now());
        return dietListRepository.save(dietList);
    }
}
