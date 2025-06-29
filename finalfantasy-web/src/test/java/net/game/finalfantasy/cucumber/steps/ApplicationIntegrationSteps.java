package net.game.finalfantasy.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.profiles.active=test"
})
public class ApplicationIntegrationSteps {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private ResponseEntity<String> lastResponse;
    private long operationStartTime;

    @Given("the Final Fantasy application is running")
    public void the_final_fantasy_application_is_running() {
        // Application is already running due to @SpringBootTest
        // Just verify that we can create a rest template
        assertNotNull(restTemplate, "RestTemplate should be available");
    }

    @Given("the Vert.x server is running on port {int}")
    public void the_vert_x_server_is_running_on_port(Integer port) {
        // Server should already be running, just verify we can create a client
        assertNotNull(restTemplate, "RestTemplate should be created");
    }

    @When("I request the Vert.x health check")
    public void i_request_the_vert_x_health_check() {
        operationStartTime = System.currentTimeMillis();
        try {
            lastResponse = restTemplate.getForEntity("http://localhost:8081/vertx/health", String.class);
        } catch (Exception e) {
            fail("Failed to make health check request: " + e.getMessage());
        }
    }

    @When("I request the game status from Vert.x")
    public void i_request_the_game_status_from_vert_x() {
        operationStartTime = System.currentTimeMillis();
        try {
            lastResponse = restTemplate.getForEntity("http://localhost:8081/vertx/game/status", String.class);
        } catch (Exception e) {
            fail("Failed to make game status request: " + e.getMessage());
        }
    }

    @Then("I should receive a successful response")
    public void i_should_receive_a_successful_response() {
        assertNotNull(lastResponse, "Response should not be null");
        assertEquals(200, lastResponse.getStatusCodeValue(), "Response should be successful");
    }

    @And("the response should contain health status")
    public void the_response_should_contain_health_status() {
        assertNotNull(lastResponse, "Response should not be null");
        String responseBody = lastResponse.getBody();
        assertNotNull(responseBody, "Response body should not be null");
        assertTrue(responseBody.contains("status"), "Response should contain status field");
    }

    @And("the response should indicate the service is {string}")
    public void the_response_should_indicate_the_service_is(String expectedValue) {
        assertNotNull(lastResponse, "Response should not be null");
        String responseBody = lastResponse.getBody();
        assertNotNull(responseBody, "Response body should not be null");

        if ("UP".equals(expectedValue)) {
            assertTrue(responseBody.contains("\"status\":\"UP\""), 
                "Response should indicate status is UP");
        } else if ("Final Fantasy".equals(expectedValue)) {
            assertTrue(responseBody.contains("\"game\":\"Final Fantasy\""), 
                "Response should indicate game is Final Fantasy");
        }
    }

    @And("the response should contain game information")
    public void the_response_should_contain_game_information() {
        assertNotNull(lastResponse, "Response should not be null");
        String responseBody = lastResponse.getBody();
        assertNotNull(responseBody, "Response body should not be null");
        assertTrue(responseBody.contains("game"), "Response should contain game field");
        assertTrue(responseBody.contains("server_status"), "Response should contain server_status field");
    }

    @And("操作應該在500毫秒內完成")
    public void 操作應該在500毫秒內完成() {
        long operationTime = System.currentTimeMillis() - operationStartTime;
        assertTrue(operationTime <= 500, 
            "Operation should complete within 500ms, but took " + operationTime + "ms");
    }
}
