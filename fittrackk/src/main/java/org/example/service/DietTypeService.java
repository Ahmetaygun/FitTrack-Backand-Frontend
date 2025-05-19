package org.example.service;

import org.example.dto.DietTypeDto;
import org.example.model.DietType;
import org.example.repository.DietTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietTypeService {

    private final DietTypeRepository dietTypeRepository;

    @Autowired
    public DietTypeService(DietTypeRepository dietTypeRepository) {
        this.dietTypeRepository = dietTypeRepository;
    }

    // Diyet tipi adı ile diyet tipi getirme
    public DietType getDietTypeByName(String name) {
        return dietTypeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Diyet tipi bulunamadı: " + name));
    }

    // Diyet tipi ekleme
    public DietType addDietType(DietType dietType) {
        return dietTypeRepository.save(dietType);
    }

    // Diyet tipi güncelleme
    public DietType updateDietType(Long id, DietType updatedDietType) {
        updatedDietType.setId(id);
        return dietTypeRepository.save(updatedDietType);
    }

    // Tüm diyet tiplerini DTO olarak getir
    public List<DietTypeDto> getAllDietTypeDtos() {
        return dietTypeRepository.findAll()
                .stream()
                .map(d -> new DietTypeDto(d.getId(), d.getName(), d.getDescription()))
                .collect(Collectors.toList());
    }
}
