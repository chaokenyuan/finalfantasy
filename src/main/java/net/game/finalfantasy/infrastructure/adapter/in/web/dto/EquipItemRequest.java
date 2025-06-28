package net.game.finalfantasy.infrastructure.adapter.in.web.dto;

public class EquipItemRequest {
    private String equipmentName;

    public EquipItemRequest() {
    }

    public EquipItemRequest(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}