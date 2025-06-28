package net.game.finalfantasy.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/game_rules_validation.feature",
        glue = {"net.game.finalfantasy.cucumber", "net.game.finalfantasy.domain"},
        plugin = {"pretty"},
        tags = "not @ignore"
)
@DisplayName("遊戲規則驗證測試")
public class GameRulesValidationTest {
}
