package net.game.finalfantasy.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Cucumber configuration for Final Fantasy Application tests.
 * This configuration provides Spring context for Cucumber tests.
 */
@CucumberContextConfiguration
@SpringBootTest(classes = net.game.finalfantasy.application.config.TestApplication.class)
@TestPropertySource(properties = {
        "logging.level.net.game.finalfantasy=DEBUG",
        "spring.main.web-application-type=none"
})
public class CucumberSpringConfiguration {

    static {
        System.out.println("[DEBUG_LOG] Final Fantasy Application Cucumber tests initialized with Spring Boot context");
    }
}
