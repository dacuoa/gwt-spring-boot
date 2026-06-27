package com.dacuoa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "inspections")
public class Inspection implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String inspectorName;

    @Column(nullable = false)
    private LocalDate inspectionDate;

    @Column(nullable = false, unique = true)
    private String bikeSerialNumber;

    @Column(nullable = false)
    private String frameCondition;

    @Column(nullable = false)
    private String brakes;

    @Column(nullable = false)
    private String tyres;

    @Column(nullable = false)
    private Boolean lightsPresent;

    @Column(length = 1000)
    private String notes;

    public Inspection() {
    }

    public Inspection(String inspectorName, LocalDate inspectionDate, String bikeSerialNumber,
                      String frameCondition, String brakes, String tyres, Boolean lightsPresent, String notes) {
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
