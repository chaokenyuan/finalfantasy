package net.game.finalfantasy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.grpc.server.enabled=false"
})
class FinalFantasyApplicationTests {

    @Test
    void contextLoads() {
    }

}
