package com.dacuoa.controller;

import com.dacuoa.dto.InspectionDTO;
import com.dacuoa.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inspections")
@CrossOrigin(origins = "*")
public class InspectionController {

    private final InspectionService service;

    @Autowired
    public InspectionController(InspectionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InspectionDTO> createInspection(@RequestBody InspectionDTO inspection) {
        InspectionDTO saved = service.saveInspection(inspection);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/search")
    public ResponseEntity<List<InspectionDTO>> search(@RequestParam String q) {
        List<InspectionDTO> results = service.search(q);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspectionDTO> getInspection(@PathVariable Long id) {
        InspectionDTO inspection = service.getById(id);
        if (inspection != null) {
            return ResponseEntity.ok(inspection);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInspection(@PathVariable Long id) {
        service.deleteInspection(id);
        return ResponseEntity.noContent().build();
    }
}
