package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.DietListRequest;
import org.example.dto.DietListResponse;
import org.example.model.DietList;
import org.example.service.DietListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diet-lists")
@RequiredArgsConstructor
public class DietListController {

    private final DietListService dietListService;

    //Diyetisyen → Seçilen hastaya diyet listesi yazar
    @PreAuthorize("hasRole('DIETITIAN')")
    @PostMapping("/write")
    public ResponseEntity<String> createDietList(@RequestHeader("Authorization") String token,
                                                 @RequestBody DietListRequest request) {
        dietListService.createDietList(token.replace("Bearer ", ""), request);
        return ResponseEntity.ok("Diyet listesi başarıyla oluşturuldu.");
    }

    //Client → Kendi diyet listesini görüntüler
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/my-list")
    public ResponseEntity<DietListResponse> getOwnDietList(@RequestHeader("Authorization") String token) {
        DietListResponse response = dietListService.getClientDietList(token.replace("Bearer ", ""));
        return ResponseEntity.ok(response);
    }

    // Admin veya Diyetisyen → Belirli hastanın tüm listeleri
    @PreAuthorize("hasAnyRole('DIETITIAN','ADMIN')")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<DietList>> getDietListsByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(dietListService.getDietListsByClientId(clientId));
    }

    //Admin veya Diyetisyen → Belirli diyet tipiyle oluşturulmuş listeler
    @PreAuthorize("hasAnyRole('DIETITIAN','ADMIN')")
    @GetMapping("/diet-type/{dietTypeId}")
    public ResponseEntity<List<DietList>> getByDietType(@PathVariable Long dietTypeId) {
        return ResponseEntity.ok(dietListService.getDietListsByDietTypeId(dietTypeId));
    }

    //Admin veya Diyetisyen → Belirli diyetisyenin tüm yazdığı listeler
    @PreAuthorize("hasAnyRole('DIETITIAN','ADMIN')")
    @GetMapping("/dietitian/{dietitianId}")
    public ResponseEntity<List<DietList>> getByDietitian(@PathVariable Long dietitianId) {
        return ResponseEntity.ok(dietListService.getDietListsByDietitianId(dietitianId));
    }

    //Diyetisyen → Var olan listeyi günceller
    @PreAuthorize("hasRole('DIETITIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<DietList> updateDietList(@PathVariable Long id,
                                                   @RequestBody DietList updatedList) {
        return ResponseEntity.ok(dietListService.updateDietList(id, updatedList));
    }

}
