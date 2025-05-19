package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AdminUserCreationRequest;
import org.example.dto.ClientAdminDto;
import org.example.dto.DietitianDto;
import org.example.model.Admin;
import org.example.service.AdminService;
import org.example.service.ClientService;
import org.example.service.DietitianService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final ClientService clientService;
    private final DietitianService dietitianService;
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> allUsers = new HashMap<>();
        allUsers.put("clients", adminService.getAllClientsAsDto());
        allUsers.put("dietitians", dietitianService.getAllDietitians());
        allUsers.put("admins", adminService.getAllAdmins()); 
        return ResponseEntity.ok(allUsers);
    }

    @DeleteMapping("/delete/client/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id") Long id) {
        adminService.deleteClientById(id);
        return ResponseEntity.ok("Client başarıyla silindi.");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dietitian/{dietitianId}/patients")
    public ResponseEntity<?> getPatientsOfDietitian(@PathVariable Long dietitianId) {
        return ResponseEntity.ok(dietitianService.getPatientsByDietitianId(dietitianId));
    }


    @DeleteMapping("/delete/dietitian/{id}")
    public ResponseEntity<String> deleteDietitian(@PathVariable("id") Long id) {
        adminService.deleteDietitianById(id);
        return ResponseEntity.ok("Diyetisyen başarıyla silindi.");
    }

    @PostMapping("/convert-to-dietitian/{clientId}")
    public ResponseEntity<DietitianDto> convertToDietitian(@PathVariable("clientId") Long clientId) {
        return ResponseEntity.ok(adminService.promoteClientToDietitian(clientId));
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody AdminUserCreationRequest request) {
        adminService.createUserAsAdmin(request);
        return ResponseEntity.ok("Kullanıcı başarıyla eklendi.");
    }
}
