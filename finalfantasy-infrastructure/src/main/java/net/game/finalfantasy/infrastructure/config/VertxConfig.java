package net.game.finalfantasy.infrastructure.config;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfig {

    @Bean
    public Vertx vertx() {
        VertxOptions options = new VertxOptions()
                .setEventLoopPoolSize(4)
                .setWorkerPoolSize(20);
        return Vertx.vertx(options);
    }
}