package net.game.finalfantasy.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * Cucumber configuration for Final Fantasy Infrastructure tests.
 * Since we've migrated from Spring Boot to Mockito for testing,
 * this configuration file is simplified and no longer requires Spring context.
 * 
 * All step definitions now use Mockito for dependency injection and mocking.
 */
@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberSpringConfiguration.TestConfig.class)
public class CucumberSpringConfiguration {

    // This class serves as a placeholder for any future Cucumber configuration needs
    // that don't require Spring Boot context.

    @Configuration
    static class TestConfig {
        // Minimal configuration to satisfy Spring context requirements
    }

    static {
        System.out.println("[DEBUG_LOG] Final Fantasy Infrastructure Cucumber tests initialized with Mockito-based testing");
    }
}
