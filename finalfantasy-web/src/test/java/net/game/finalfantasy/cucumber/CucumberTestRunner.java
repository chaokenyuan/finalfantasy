package net.game.finalfantasy.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Consolidated Cucumber Test Runner for Final Fantasy Game
 * This runner executes all integration tests using the consolidated test structure
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "net.game.finalfantasy.cucumber.domain",
                "net.game.finalfantasy.cucumber"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/html",
                "json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml"
        },
        tags = "not @ignore"
)
public class CucumberTestRunner {
}
