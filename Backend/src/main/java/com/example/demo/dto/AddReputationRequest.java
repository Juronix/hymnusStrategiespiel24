package com.example.demo.dto;

public class AddReputationRequest {
    private String teamName;
    private double reputationAmount;

    // Getter und Setter
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getReputationAmount() {
        return reputationAmount;
    }

    public void setReputationAmount(double reputationAmount) {
        this.reputationAmount = reputationAmount;
    }
}
