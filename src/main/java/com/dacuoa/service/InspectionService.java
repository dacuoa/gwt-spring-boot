package com.dacuoa.service;

import com.dacuoa.dto.InspectionDTO;
import com.dacuoa.model.Inspection;
import com.dacuoa.repository.InspectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InspectionService {
    private final InspectionRepository repository;

    @Autowired
    public InspectionService(InspectionRepository repository) {
        this.repository = repository;
    }

    public InspectionDTO saveInspection(InspectionDTO dto) {
        Inspection inspection;
        if (dto.getId() != null) {
            inspection = repository.findById(dto.getId()).orElse(new Inspection());
        } else {
            inspection = new Inspection();
        }

        inspection.setInspectorName(dto.getInspectorName());
        inspection.setInspectionDate(dto.getInspectionDate());
        inspection.setBikeSerialNumber(dto.getBikeSerialNumber());
        inspection.setFrameCondition(dto.getFrameCondition());
        inspection.setBrakes(dto.getBrakes());
        inspection.setTyres(dto.getTyres());
        inspection.setLightsPresent(dto.getLightsPresent());
        inspection.setNotes(dto.getNotes());

        Inspection saved = repository.save(inspection);
        return toDTO(saved);
    }

    public List<InspectionDTO> search(String searchTerm) {
        List<Inspection> results = repository.searchByInspectorNameOrSerialNumber(searchTerm);
        return results.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public InspectionDTO getById(Long id) {
        return repository.findById(id).map(this::toDTO).orElse(null);
    }

    public void deleteInspection(Long id) {
        repository.deleteById(id);
    }

    private InspectionDTO toDTO(Inspection inspection) {
        return new InspectionDTO(
                inspection.getId(),
                inspection.getInspectorName(),
                inspection.getInspectionDate(),
                inspection.getBikeSerialNumber(),
                inspection.getFrameCondition(),
                inspection.getBrakes(),
                inspection.getTyres(),
                inspection.getLightsPresent(),
                inspection.getNotes()
        );
    }
}
