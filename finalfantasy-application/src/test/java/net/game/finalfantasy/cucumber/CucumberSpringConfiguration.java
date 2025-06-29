package net.game.finalfantasy.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import net.game.finalfantasy.application.config.TestApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ComponentScan(basePackages = {
        "net.game.finalfantasy.domain",
        "net.game.finalfantasy.application",
        "net.game.finalfantasy.infrastructure",
        "net.game.finalfantasy.cucumber"
})
@TestPropertySource(properties = {
        "logging.level.net.game.finalfantasy=DEBUG"
})
public class CucumberSpringConfiguration {
}
