package net.game.finalfantasy.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfiguration.class)
@TestPropertySource(properties = {
    "spring.grpc.server.enabled=false"
})
class ServerPortsConfigTest {

    @Autowired
    private ServerPortsConfig serverPortsConfig;

    @Test
    void testDefaultConfiguration() {
        assertNotNull(serverPortsConfig);
        assertNotNull(serverPortsConfig.getHttp());
        assertNotNull(serverPortsConfig.getGrpc());
        assertNotNull(serverPortsConfig.getVertx());

        // Test default values from local profile (which is active by default)
        assertEquals(8080, serverPortsConfig.getHttp().getPort());
        assertEquals(9090, serverPortsConfig.getGrpc().getPort());
        assertTrue(serverPortsConfig.getGrpc().isEnabled());
        assertEquals(8081, serverPortsConfig.getVertx().getHttpPort());
        assertEquals(8082, serverPortsConfig.getVertx().getSocketPort());
    }
}

@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("sit")
@TestPropertySource(properties = {
    "spring.grpc.server.enabled=false"
})
class ServerPortsConfigSitTest {

    @Autowired
    private ServerPortsConfig serverPortsConfig;

    @Test
    void testSitConfiguration() {
        assertNotNull(serverPortsConfig);

        // Test SIT environment values
        assertEquals(8180, serverPortsConfig.getHttp().getPort());
        assertEquals(9190, serverPortsConfig.getGrpc().getPort());
        assertTrue(serverPortsConfig.getGrpc().isEnabled());
        assertEquals(8181, serverPortsConfig.getVertx().getHttpPort());
        assertEquals(8182, serverPortsConfig.getVertx().getSocketPort());
    }
}

@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("uat")
@TestPropertySource(properties = {
    "spring.grpc.server.enabled=false"
})
class ServerPortsConfigUatTest {

    @Autowired
    private ServerPortsConfig serverPortsConfig;

    @Test
    void testUatConfiguration() {
        assertNotNull(serverPortsConfig);

        // Test UAT environment values
        assertEquals(8280, serverPortsConfig.getHttp().getPort());
        assertEquals(9290, serverPortsConfig.getGrpc().getPort());
        assertTrue(serverPortsConfig.getGrpc().isEnabled());
        assertEquals(8281, serverPortsConfig.getVertx().getHttpPort());
        assertEquals(8282, serverPortsConfig.getVertx().getSocketPort());
    }
}

@SpringBootTest(classes = TestConfiguration.class)
@ActiveProfiles("prod")
@TestPropertySource(properties = {
    "spring.grpc.server.enabled=false"
})
class ServerPortsConfigProdTest {

    @Autowired
    private ServerPortsConfig serverPortsConfig;

    @Test
    void testProdConfiguration() {
        assertNotNull(serverPortsConfig);

        // Test Production environment values
        assertEquals(8080, serverPortsConfig.getHttp().getPort());
        assertEquals(9090, serverPortsConfig.getGrpc().getPort());
        assertTrue(serverPortsConfig.getGrpc().isEnabled());
        assertEquals(8081, serverPortsConfig.getVertx().getHttpPort());
        assertEquals(8082, serverPortsConfig.getVertx().getSocketPort());
    }
}
