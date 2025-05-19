package org.example.controller;

import java.util.List;

import org.example.dto.DietTypeDto;
import org.example.model.DietType;
import org.example.service.DietTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diet-types")
public class DietTypeController {

    private final DietTypeService dietTypeService;

    @Autowired
    public DietTypeController(DietTypeService dietTypeService) {
        this.dietTypeService = dietTypeService;
    }

    // Diyet tipi oluşturma
    @PostMapping
    public ResponseEntity<DietType> createDietType(@RequestBody DietType dietType) {
        DietType createdDietType = dietTypeService.addDietType(dietType);
        return ResponseEntity.ok(createdDietType);
    }

    // Diyet tipi adı ile arama
    @GetMapping("/name/{name}")
    public ResponseEntity<DietType> getDietTypeByName(@PathVariable String name) {
        DietType dietType = dietTypeService.getDietTypeByName(name);
        if (dietType != null) {
            return ResponseEntity.ok(dietType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Diyet tipi güncelleme
    @PutMapping("/{id}")
    public ResponseEntity<DietType> updateDietType(@PathVariable Long id, @RequestBody DietType updatedDietType) {
        DietType dietType = dietTypeService.updateDietType(id, updatedDietType);
        return ResponseEntity.ok(dietType);
    }


    // DTO ile diyet tiplerini getir
@GetMapping("/all")
public ResponseEntity<List<DietTypeDto>> getAllDietTypesAsDto() {
    List<DietTypeDto> dietTypes = dietTypeService.getAllDietTypeDtos();
    return ResponseEntity.ok(dietTypes);
}


}
