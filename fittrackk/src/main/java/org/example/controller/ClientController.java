package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.model.Client;
import org.example.model.HealthInfo;
import org.example.service.ClientService;
import org.example.service.DietListService;
import org.example.service.HealthInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@PreAuthorize("hasRole('CLIENT')")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final HealthInfoService healthInfoService;
    private final DietListService dietListService;

    // Kendi bilgilerini getir
    @GetMapping("/me")
    public ResponseEntity<ClientSelfDto> getOwnClientInfo(@RequestHeader("Authorization") String token) {
        String email = clientService.extractEmailFromToken(token);
        ClientSelfDto dto = clientService.getSelfInfo(email);
        return ResponseEntity.ok(dto);
    }

    // Sağlık bilgisi oluştur veya güncelle
    @PostMapping("/health-info")
    public ResponseEntity<String> createOrUpdateHealthInfo(@RequestHeader("Authorization") String token,
                                                           @RequestBody HealthInfoRequest request) {
        healthInfoService.saveOrUpdateHealthInfo(token.replace("Bearer ", ""), request);
        return ResponseEntity.ok("Sağlık bilgisi kaydedildi veya güncellendi.");
    }

    // Sağlık bilgisi görüntüleme
    @GetMapping("/health-info")
    public ResponseEntity<?> getHealthInfo(@RequestHeader("Authorization") String token) {
        HealthInfoRequest dto = healthInfoService.getHealthInfoByToken(token.replace("Bearer ", ""));
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kayıtlı sağlık bilgisi bulunamadı.");
        }
        return ResponseEntity.ok(dto);
    }

    // Diyet tipi seçme
    @PostMapping("/select-diet")
    public ResponseEntity<String> selectDietType(@RequestHeader("Authorization") String token,
                                                 @RequestBody DietSelectionRequest request) {
        clientService.assignDietType(token.replace("Bearer ", ""), request.getDietTypeId());
        return ResponseEntity.ok("Diyet tipi başarıyla seçildi.");
    }

    // Diyetisyen seçme
    @PostMapping("/select-dietitian")
    public ResponseEntity<String> selectDietitian(@RequestHeader("Authorization") String token,
                                                  @RequestBody DietitianSelectionRequest request) {
        clientService.assignDietitian(token.replace("Bearer ", ""), request.getDietitianId());
        return ResponseEntity.ok("Diyetisyen başarıyla atandı.");
    }

    // Diyet listesi görüntüleme (hasta)
    @GetMapping("/diet-list")
    public ResponseEntity<DietListResponse> getClientDietList(@RequestHeader("Authorization") String token) {
        DietListResponse list = dietListService.getClientDietList(token.replace("Bearer ", ""));
        return ResponseEntity.ok(list);
    }
}
