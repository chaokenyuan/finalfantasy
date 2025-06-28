package net.game.finalfantasy.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI finalFantasyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Final Fantasy Game API")
                        .description("REST API for Final Fantasy game management system. " +
                                   "This API provides endpoints for hero management, equipment system, " +
                                   "and game status monitoring.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Final Fantasy Development Team")
                                .email("dev@finalfantasy.game"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}