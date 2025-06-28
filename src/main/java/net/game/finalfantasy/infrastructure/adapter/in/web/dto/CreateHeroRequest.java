package net.game.finalfantasy.infrastructure.adapter.in.web.dto;

public class CreateHeroRequest {
    private String name;
    private String heroType;

    public CreateHeroRequest() {
    }

    public CreateHeroRequest(String name, String heroType) {
        this.name = name;
        this.heroType = heroType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
    }
}