package net.game.finalfantasy.cucumber;

import net.game.finalfantasy.application.port.in.HeroManagementUseCase;
import net.game.finalfantasy.application.port.out.HeroRepository;
import net.game.finalfantasy.application.service.HeroManagementService;
import net.game.finalfantasy.infrastructure.adapter.out.persistence.InMemoryHeroRepository;

public class TestConfiguration {
    private static HeroRepository sharedRepository;
    private static HeroManagementUseCase sharedHeroManagementUseCase;
    
    public static synchronized HeroRepository getSharedRepository() {
        if (sharedRepository == null) {
            sharedRepository = new InMemoryHeroRepository();
        }
        return sharedRepository;
    }
    
    public static synchronized HeroManagementUseCase getSharedHeroManagementUseCase() {
        if (sharedHeroManagementUseCase == null) {
            sharedHeroManagementUseCase = new HeroManagementService(getSharedRepository());
        }
        return sharedHeroManagementUseCase;
    }
    
    public static void clearAll() {
        if (sharedRepository != null) {
            sharedRepository.deleteAll();
        }
    }
}