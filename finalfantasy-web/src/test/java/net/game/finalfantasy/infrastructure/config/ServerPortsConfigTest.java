package net.game.finalfantasy.infrastructure.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ServerPortsConfig 測試")
class ServerPortsConfigTest {

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(textBlock = """
            # description
            'GIVEN: 預設設定 WHEN: 建立ServerPortsConfig THEN: 回傳預設埠號值'
            """)
    @DisplayName("GIVEN: 預設設定 WHEN: 建立ServerPortsConfig THEN: 應回傳預設埠號值")
    void testDefaultConfiguration(String description) {
        // Given
        ServerPortsConfig config = givenDefaultServerPortsConfig();
        
        // When
        ServerPortsConfig result = whenCreateConfig(config);
        
        // Then
        thenShouldHaveDefaultValues(result);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(textBlock = """
            # description
            'GIVEN: SIT環境設定 WHEN: 設定SIT埠號 THEN: 回傳SIT環境埠號值'
            """)
    @DisplayName("GIVEN: SIT環境設定 WHEN: 設定SIT埠號 THEN: 應回傳SIT環境埠號值")
    void testSitConfiguration(String description) {
        // Given
        ServerPortsConfig config = givenSitServerPortsConfig();
        
        // When
        ServerPortsConfig result = whenCreateConfig(config);
        
        // Then
        thenShouldHaveSitValues(result);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(textBlock = """
            # description
            'GIVEN: UAT環境設定 WHEN: 設定UAT埠號 THEN: 回傳UAT環境埠號值'
            """)
    @DisplayName("GIVEN: UAT環境設定 WHEN: 設定UAT埠號 THEN: 應回傳UAT環境埠號值")
    void testUatConfiguration(String description) {
        // Given
        ServerPortsConfig config = givenUatServerPortsConfig();
        
        // When
        ServerPortsConfig result = whenCreateConfig(config);
        
        // Then
        thenShouldHaveUatValues(result);
    }

    private ServerPortsConfig givenDefaultServerPortsConfig() {
        return new ServerPortsConfig();
    }

    private ServerPortsConfig givenSitServerPortsConfig() {
        ServerPortsConfig config = new ServerPortsConfig();
        config.getHttp().setPort(8180);
        config.getGrpc().setPort(9190);
        config.getGrpc().setEnabled(true);
        config.getVertx().setHttpPort(8181);
        config.getVertx().setSocketPort(8182);
        return config;
    }

    private ServerPortsConfig givenUatServerPortsConfig() {
        ServerPortsConfig config = new ServerPortsConfig();
        config.getHttp().setPort(8280);
        config.getGrpc().setPort(9290);
        config.getGrpc().setEnabled(true);
        config.getVertx().setHttpPort(8281);
        config.getVertx().setSocketPort(8282);
        return config;
    }

    private ServerPortsConfig whenCreateConfig(ServerPortsConfig config) {
        return config;
    }

    private void thenShouldHaveDefaultValues(ServerPortsConfig result) {
        assertNotNull(result);
        assertNotNull(result.getHttp());
        assertNotNull(result.getGrpc());
        assertNotNull(result.getVertx());
        assertEquals(8080, result.getHttp().getPort());
        assertEquals(9090, result.getGrpc().getPort());
        assertTrue(result.getGrpc().isEnabled());
        assertEquals(8081, result.getVertx().getHttpPort());
        assertEquals(8082, result.getVertx().getSocketPort());
    }

    private void thenShouldHaveSitValues(ServerPortsConfig result) {
        assertNotNull(result);
        assertEquals(8180, result.getHttp().getPort());
        assertEquals(9190, result.getGrpc().getPort());
        assertTrue(result.getGrpc().isEnabled());
        assertEquals(8181, result.getVertx().getHttpPort());
        assertEquals(8182, result.getVertx().getSocketPort());
    }

    private void thenShouldHaveUatValues(ServerPortsConfig result) {
        assertNotNull(result);
        assertEquals(8280, result.getHttp().getPort());
        assertEquals(9290, result.getGrpc().getPort());
        assertTrue(result.getGrpc().isEnabled());
        assertEquals(8281, result.getVertx().getHttpPort());
        assertEquals(8282, result.getVertx().getSocketPort());
    }
}
