Feature: Final Fantasy Game Application
  As a game developer
  I want to test the Final Fantasy application
  So that I can ensure both Spring Boot and Vert.x components work correctly

  Background:
    Given the Final Fantasy application is running

  Scenario: Test Vert.x Health Check
    Given the Vert.x server is running on port 8081
    When I request the Vert.x health check
    Then I should receive a successful response
    And the response should contain health status
    And the response should indicate the service is "UP"

  Scenario: Test Vert.x Game Status
    Given the Vert.x server is running on port 8081
    When I request the game status from Vert.x
    Then I should receive a successful response
    And the response should contain game information
    And the response should indicate the service is "Final Fantasy"

  @integration
  Scenario: Verify Vert.x and Spring Boot Integration
    Given the Final Fantasy application is running
    And the Vert.x server is running on port 8081
    When I request the Vert.x health check
    Then I should receive a successful response
    And the response should contain health status