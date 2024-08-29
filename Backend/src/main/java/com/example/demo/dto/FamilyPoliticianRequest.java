package com.example.demo.dto;

public class FamilyPoliticianRequest {

    private String familyId;
    private int politicianId;

    // Getter und Setter
    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public int getPoliticianId() {
        return politicianId;
    }

    public void setPoliticianId(int politicianId) {
        this.politicianId = politicianId;
    }
}
