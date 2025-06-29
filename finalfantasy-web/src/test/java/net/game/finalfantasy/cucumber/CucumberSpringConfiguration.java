package net.game.finalfantasy.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(classes = net.game.finalfantasy.FinalFantasyApplication.class)
@TestPropertySource(properties = {
        "logging.level.net.game.finalfantasy=DEBUG",
        "spring.main.web-application-type=none"
})
public class CucumberSpringConfiguration {
}
