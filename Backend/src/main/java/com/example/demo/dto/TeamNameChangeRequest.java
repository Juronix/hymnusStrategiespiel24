package com.example.demo.dto;

public class TeamNameChangeRequest {
    private String oldName;
    private String newName;

    // Getter und Setter
    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
