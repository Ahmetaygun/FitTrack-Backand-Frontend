package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TrackingResponseDto;
import org.example.model.Dietitian;
import org.example.service.DietitianService;
import org.example.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.example.dto.DietitianDto;
import org.example.dto.DietListResponse;

import java.util.List;

@RestController
@RequestMapping("/api/dietitians")
@RequiredArgsConstructor
public class DietitianController {
    private final TrackingService trackingService;
    private final DietitianService dietitianService;

    // Yalnızca ADMIN tarafından kullanılabilir: yeni diyetisyen ekleme
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Dietitian> registerDietitian(@RequestBody Dietitian dietitian) {
        return ResponseEntity.ok(dietitianService.registerDietitian(dietitian));
    }

    // Hem ADMIN hem CLIENT diyetisyenleri listeleyebilir
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @GetMapping
    public ResponseEntity<List<DietitianDto>> getAllDietitians() {
        return ResponseEntity.ok(dietitianService.getAllDietitians());
    }

    // Belirli ID ile diyetisyen getir (sadece ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Dietitian> getDietitianById(@PathVariable Long id) {
        return ResponseEntity.ok(dietitianService.getDietitianById(id));
    }

    // Diyetisyen sil (sadece ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDietitian(@PathVariable Long id) {
        dietitianService.deleteDietitian(id);
        return ResponseEntity.ok("Diyetisyen başarıyla silindi!");
    }

    // 🟢 Eksik olan my-clients endpointi eklendi!
    @PreAuthorize("hasRole('DIETITIAN')")
    @GetMapping("/my-patients")
    public ResponseEntity<?> getMyAssignedClients(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        return ResponseEntity.ok(dietitianService.getAssignedClients(jwt));
    }

    @PreAuthorize("hasRole('DIETITIAN')")
    @GetMapping("/my-diet-lists")
    public ResponseEntity<List<DietListResponse>> getMyDietLists(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        return ResponseEntity.ok(dietitianService.getDietLists(jwt));
    }


}
