package net.game.finalfantasy.infrastructure.adapter.in.web;

import net.game.finalfantasy.application.port.in.HeroManagementUseCase;
import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.infrastructure.adapter.in.web.dto.CreateHeroRequest;
import net.game.finalfantasy.infrastructure.adapter.in.web.dto.EquipItemRequest;
import net.game.finalfantasy.infrastructure.adapter.in.web.dto.HeroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/heroes")
public class HeroController {
    
    private final HeroManagementUseCase heroManagementUseCase;
    
    public HeroController(HeroManagementUseCase heroManagementUseCase) {
        this.heroManagementUseCase = heroManagementUseCase;
    }
    
    @PostMapping
    public ResponseEntity<HeroResponse> createHero(@RequestBody CreateHeroRequest request) {
        try {
            Hero hero = heroManagementUseCase.createHero(request.getName(), request.getHeroType());
            return ResponseEntity.status(HttpStatus.CREATED).body(HeroResponse.from(hero));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{name}")
    public ResponseEntity<HeroResponse> getHero(@PathVariable String name) {
        try {
            Hero hero = heroManagementUseCase.getHero(name);
            return ResponseEntity.ok(HeroResponse.from(hero));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{name}/equip")
    public ResponseEntity<HeroResponse> equipItem(@PathVariable String name, 
                                                 @RequestBody EquipItemRequest request) {
        try {
            heroManagementUseCase.equipItem(name, request.getEquipmentName());
            Hero hero = heroManagementUseCase.getHero(name);
            return ResponseEntity.ok(HeroResponse.from(hero));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{name}/unequip/{slot}")
    public ResponseEntity<HeroResponse> unequipItem(@PathVariable String name, 
                                                   @PathVariable String slot) {
        try {
            heroManagementUseCase.unequipItem(name, slot);
            Hero hero = heroManagementUseCase.getHero(name);
            return ResponseEntity.ok(HeroResponse.from(hero));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{name}/exists")
    public ResponseEntity<Boolean> heroExists(@PathVariable String name) {
        boolean exists = heroManagementUseCase.heroExists(name);
        return ResponseEntity.ok(exists);
    }
}