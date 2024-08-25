package com.example.demo.dto;

public class CreateNewTradeUnitRequest {
    private boolean isLandTradeUnit;
    private int unitLevel;
    private int teamId;
    private int cityId1;
    private int cityId2;

    public boolean getIsLandTradeUnit() {
        return isLandTradeUnit;
    }

    public void setIsLandTradeUnit(boolean isLandTradeUnit) {
        this.isLandTradeUnit = isLandTradeUnit;
    }

    public int getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getCityId1() {
        return cityId1;
    }

    public void setCityId1(int cityId1) {
        this.cityId1 = cityId1;
    }

    public int getCityId2() {
        return cityId2;
    }

    public void setCityId2(int cityId2) {
        this.cityId2 = cityId2;
    }
}
