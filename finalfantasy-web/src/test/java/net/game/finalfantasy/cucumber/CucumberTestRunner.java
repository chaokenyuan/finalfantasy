package net.game.finalfantasy.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "net.game.finalfantasy.cucumber",
                "net.game.finalfantasy.cucumber.domain",
                "net.game.finalfantasy.cucumber.application",
                "net.game.finalfantasy.infrastructure.adapter.in.web"
        },
        plugin = {"pretty"},
        tags = "not @ignore"
)
public class CucumberTestRunner {
}
