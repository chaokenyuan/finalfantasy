package net.game.finalfantasy.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ComponentScan(basePackages = {
        "net.game.finalfantasy",
        "net.game.finalfantasy.cucumber.steps",
        "net.game.finalfantasy.application",
        "net.game.finalfantasy.domain",
        "net.game.finalfantasy.infrastructure"
})
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "logging.level.net.game.finalfantasy=DEBUG"
})
public class CucumberSpringConfiguration {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TestRestTemplate testRestTemplate() {
            return new TestRestTemplate();
        }
    }
}
