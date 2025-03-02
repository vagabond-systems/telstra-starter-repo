package stepDefinitions;//public class stepDefinitions.RunCucumberTest {
//}

import org.junit.Test;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/sim_card_activator.feature",
        glue = "stepDefinitions",
//        plugin = {"pretty", "html:target/cucumber-reports"}
        plugin = {"pretty", "json:target/cucumber-report.json"}
)
public class RunCucumberTest {

}
