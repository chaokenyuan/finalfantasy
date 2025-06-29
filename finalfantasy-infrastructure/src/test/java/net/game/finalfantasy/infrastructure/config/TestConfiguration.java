package net.game.finalfantasy.infrastructure.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServerPortsConfig.class)
public class TestConfiguration {
}
