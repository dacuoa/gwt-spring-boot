package com.dacuoa.dto;

import java.time.LocalDate;

public class InspectionDTO {
    private Long id;
    private String inspectorName;
    private LocalDate inspectionDate;
    private String bikeSerialNumber;
    private String frameCondition;
    private String brakes;
    private String tyres;
    private Boolean lightsPresent;
    private String notes;

    public InspectionDTO() {
    }

    public InspectionDTO(Long id, String inspectorName, LocalDate inspectionDate, String bikeSerialNumber,
                         String frameCondition, String brakes, String tyres, Boolean lightsPresent, String notes) {
        this.id = id;
        this.inspectorName = inspectorName;
        this.inspectionDate = inspectionDate;
        this.bikeSerialNumber = bikeSerialNumber;
        this.frameCondition = frameCondition;
        this.brakes = brakes;
        this.tyres = tyres;
        this.lightsPresent = lightsPresent;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getBikeSerialNumber() {
        return bikeSerialNumber;
    }

    public void setBikeSerialNumber(String bikeSerialNumber) {
        this.bikeSerialNumber = bikeSerialNumber;
    }

    public String getFrameCondition() {
        return frameCondition;
    }

    public void setFrameCondition(String frameCondition) {
        this.frameCondition = frameCondition;
    }

    public String getBrakes() {
        return brakes;
    }

    public void setBrakes(String brakes) {
        this.brakes = brakes;
    }

    public String getTyres() {
        return tyres;
    }

    public void setTyres(String tyres) {
        this.tyres = tyres;
    }

    public Boolean getLightsPresent() {
        return lightsPresent;
    }

    public void setLightsPresent(Boolean lightsPresent) {
        this.lightsPresent = lightsPresent;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
