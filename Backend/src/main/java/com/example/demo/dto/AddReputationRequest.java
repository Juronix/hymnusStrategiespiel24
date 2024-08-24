package com.example.demo.dto;

public class AddReputationRequest {
    private int id;
    private double reputation;

    // Getter und Setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getReputation() {
        return reputation;
    }

    public void setReputationAmount(double reputation) {
        this.reputation = reputation;
    }
}
