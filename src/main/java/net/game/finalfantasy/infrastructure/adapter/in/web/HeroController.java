package net.game.finalfantasy.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Tag(name = "Hero Management", description = "API endpoints for managing heroes in the Final Fantasy game")
public class HeroController {

    private final HeroManagementUseCase heroManagementUseCase;

    @PostMapping
    @Operation(summary = "Create a new hero", description = "Creates a new hero with the specified name and type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hero created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<HeroResponse> createHero(@RequestBody CreateHeroRequest request) {
        try {
            Hero hero = heroManagementUseCase.createHero(request.getName(), request.getHeroType());
            return ResponseEntity.status(HttpStatus.CREATED).body(HeroResponse.from(hero));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get hero by name", description = "Retrieves a hero's information by their name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hero found successfully"),
            @ApiResponse(responseCode = "404", description = "Hero not found")
    })
    public ResponseEntity<HeroResponse> getHero(
            @Parameter(description = "Name of the hero to retrieve", required = true)
            @PathVariable String name) {
        try {
            Hero hero = heroManagementUseCase.getHero(name);
            return ResponseEntity.ok(HeroResponse.from(hero));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{name}/equip")
    @Operation(summary = "Equip item to hero", description = "Equips an item to the specified hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item equipped successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or equipment failed")
    })
    public ResponseEntity<HeroResponse> equipItem(
            @Parameter(description = "Name of the hero to equip item to", required = true)
            @PathVariable String name, 
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
    @Operation(summary = "Unequip item from hero", description = "Removes an item from the specified equipment slot of a hero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item unequipped successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or unequip failed")
    })
    public ResponseEntity<HeroResponse> unequipItem(
            @Parameter(description = "Name of the hero to unequip item from", required = true)
            @PathVariable String name, 
            @Parameter(description = "Equipment slot to unequip from", required = true)
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
    @Operation(summary = "Check if hero exists", description = "Checks whether a hero with the specified name exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> heroExists(
            @Parameter(description = "Name of the hero to check", required = true)
            @PathVariable String name) {
        boolean exists = heroManagementUseCase.heroExists(name);
        return ResponseEntity.ok(exists);
    }
}
