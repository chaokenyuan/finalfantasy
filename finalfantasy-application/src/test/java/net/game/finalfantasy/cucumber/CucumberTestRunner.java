package net.game.finalfantasy.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "net.game.finalfantasy.cucumber",
                "net.game.finalfantasy.application",
                "net.game.finalfantasy.domain"
        },
        plugin = {"pretty"},
        tags = "not @ignore"
)
public class CucumberTestRunner {
}
