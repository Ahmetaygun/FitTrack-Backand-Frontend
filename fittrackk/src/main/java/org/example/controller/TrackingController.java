package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TrackingResponseDto;
import org.example.model.Client;
import org.example.repository.AdminRepository;
import org.example.repository.ClientRepository;
import org.example.service.JwtService;
import org.example.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;
    private final ClientRepository clientRepository;
    private final JwtService jwtService;
    private final AdminRepository adminRepository;

    //Belirli bir client'ın tracking kayıtları (diyetisyen ya da admin)
    @PreAuthorize("hasAnyRole('DIETITIAN', 'ADMIN')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> getTrackingByClientId(@PathVariable Long clientId,
                                                   @RequestHeader("Authorization") String token) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Danışan bulunamadı."));

        String email = jwtService.extractUsername(token.replace("Bearer ", ""));

        //Erişim kontrolü: Eğer bu danışanın diyetisyeniysen veya adminsense izin ver
        boolean isOwnerDietitian = client.getDietitian() != null &&
                client.getDietitian().getEmail().equals(email);

        boolean isAdmin = adminRepository.findByEmail(email).isPresent();

        if (isOwnerDietitian || isAdmin) {
            return ResponseEntity.ok(trackingService.getTrackingDtosByClient(client));
        }

        return ResponseEntity.status(403).body("Bu danışanın takip verilerine erişim yetkiniz yok.");
    }

    //Admin tüm takip kayıtlarını görebilir
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<TrackingResponseDto>> getAllTracking() {
        return ResponseEntity.ok(trackingService.getAllTrackingDtos());
    }

    //Diyetisyen sadece kendisini seçen danışanların takip kayıtlarını topluca görebilir
    @PreAuthorize("hasRole('DIETITIAN')")
    @GetMapping("/my-clients")
    public ResponseEntity<List<TrackingResponseDto>> getTrackingsForMyClients(
            @RequestHeader("Authorization") String token) {

        String jwt = token.replace("Bearer ", "");
        return ResponseEntity.ok(trackingService.getTrackingDtosForCurrentDietitian(jwt));
    }

}
