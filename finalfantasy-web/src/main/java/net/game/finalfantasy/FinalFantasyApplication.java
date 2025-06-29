package net.game.finalfantasy;

import net.game.finalfantasy.infrastructure.config.ServerPortsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ServerPortsConfig.class)
public class FinalFantasyApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalFantasyApplication.class, args);
    }

}
