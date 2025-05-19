package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.HealthInfoRequest;
import org.example.model.HealthInfo;
import org.example.service.HealthInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/healthInfos")
@RequiredArgsConstructor
public class HealthInfoController {

    private final HealthInfoService healthInfoService;

    // Tüm sağlık bilgilerini listele
    @GetMapping("/")
    public List<HealthInfo> getAllHealthInfos() {
        return healthInfoService.getAllHealthInfos();
    }

    // DTO ile sağlık bilgisi kaydetme (token alınacak)
    @PostMapping("/create")
    public ResponseEntity<String> createHealthInfo(@RequestBody HealthInfoRequest request,
                                                   @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        healthInfoService.saveOrUpdateHealthInfo(token, request);
        return ResponseEntity.ok("Sağlık bilgisi kaydedildi.");
    }

    // ID ile getir
    @GetMapping("/{id}")
    public ResponseEntity<HealthInfo> getHealthInfoById(@PathVariable Long id) {
        Optional<HealthInfo> healthInfo = healthInfoService.getHealthInfoById(id);
        return healthInfo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
