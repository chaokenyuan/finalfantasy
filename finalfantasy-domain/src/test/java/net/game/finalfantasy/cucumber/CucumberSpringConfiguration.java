package net.game.finalfantasy.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@ContextConfiguration(classes = CucumberSpringConfiguration.TestConfig.class)
@TestPropertySource(properties = {
        "logging.level.net.game.finalfantasy=DEBUG"
})
public class CucumberSpringConfiguration {

    @Configuration
    @ComponentScan(basePackages = {
            "net.game.finalfantasy.domain",
            "net.game.finalfantasy.cucumber"
    })
    static class TestConfig {
    }
}
