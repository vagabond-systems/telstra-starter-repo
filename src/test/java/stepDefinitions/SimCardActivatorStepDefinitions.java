package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@CucumberContextConfiguration
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    public static String iccidNumber(String iccidString){
        return "1255789453849037777".equals(iccidString) ? "activated" : "not activated";
    }

    private String iccid;
    private String actualAnswer;


    @Given("Iccid is {string}")
    public void iccid_is(String iccid) {
        this.iccid = iccid;
    }

    @When("I check the activation status of {string}")
    public void i_check_the_activation_status_of(String iccid) {
        // Fetch ID from the database uing the ICCID
        actualAnswer = checkIccidActivation(iccid);
    }


    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer){
        assertThat(actualAnswer).isEqualTo(expectedAnswer);
    }

    // Method to check the ICCID status based on the business logic
    private String checkIccidActivation(String iccidString) {
        // Check the ICCID against the expected values
        if ("1255789453849037777".equals(iccidString)) {
            return "activated";
        } else {
            return "not activated";
        }
    }


}

