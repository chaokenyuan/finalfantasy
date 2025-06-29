package net.game.finalfantasy.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ServerPortsConfigTest {

    private ServerPortsConfig serverPortsConfig;

    @BeforeEach
    void setUp() {
        serverPortsConfig = new ServerPortsConfig();
    }

    @Test
    void testDefaultConfiguration() {
        assertNotNull(serverPortsConfig);
        assertNotNull(serverPortsConfig.getHttp());
        assertNotNull(serverPortsConfig.getGrpc());
        assertNotNull(serverPortsConfig.getVertx());

        // Test default values
        assertEquals(8080, serverPortsConfig.getHttp().getPort());
        assertEquals(9090, serverPortsConfig.getGrpc().getPort());
        assertTrue(serverPortsConfig.getGrpc().isEnabled());
        assertEquals(8081, serverPortsConfig.getVertx().getHttpPort());
        assertEquals(8082, serverPortsConfig.getVertx().getSocketPort());
    }

    @Test
    void testSitConfiguration() {
        ServerPortsConfig sitConfig = new ServerPortsConfig();

        // Manually set SIT environment values
        sitConfig.getHttp().setPort(8180);
        sitConfig.getGrpc().setPort(9190);
        sitConfig.getGrpc().setEnabled(true);
        sitConfig.getVertx().setHttpPort(8181);
        sitConfig.getVertx().setSocketPort(8182);

        assertNotNull(sitConfig);
        assertEquals(8180, sitConfig.getHttp().getPort());
        assertEquals(9190, sitConfig.getGrpc().getPort());
        assertTrue(sitConfig.getGrpc().isEnabled());
        assertEquals(8181, sitConfig.getVertx().getHttpPort());
        assertEquals(8182, sitConfig.getVertx().getSocketPort());
    }

    @Test
    void testUatConfiguration() {
        ServerPortsConfig uatConfig = new ServerPortsConfig();

        // Manually set UAT environment values
        uatConfig.getHttp().setPort(8280);
        uatConfig.getGrpc().setPort(9290);
        uatConfig.getGrpc().setEnabled(true);
        uatConfig.getVertx().setHttpPort(8281);
        uatConfig.getVertx().setSocketPort(8282);

        assertNotNull(uatConfig);
        assertEquals(8280, uatConfig.getHttp().getPort());
        assertEquals(9290, uatConfig.getGrpc().getPort());
        assertTrue(uatConfig.getGrpc().isEnabled());
        assertEquals(8281, uatConfig.getVertx().getHttpPort());
        assertEquals(8282, uatConfig.getVertx().getSocketPort());
    }

    @Test
    void testProdConfiguration() {
        ServerPortsConfig prodConfig = new ServerPortsConfig();

        // Manually set Production environment values (same as default)
        prodConfig.getHttp().setPort(8080);
        prodConfig.getGrpc().setPort(9090);
        prodConfig.getGrpc().setEnabled(true);
        prodConfig.getVertx().setHttpPort(8081);
        prodConfig.getVertx().setSocketPort(8082);

        assertNotNull(prodConfig);
        assertEquals(8080, prodConfig.getHttp().getPort());
        assertEquals(9090, prodConfig.getGrpc().getPort());
        assertTrue(prodConfig.getGrpc().isEnabled());
        assertEquals(8081, prodConfig.getVertx().getHttpPort());
        assertEquals(8082, prodConfig.getVertx().getSocketPort());
    }
}
