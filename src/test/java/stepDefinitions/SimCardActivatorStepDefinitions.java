package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.models.SimCard;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {



    private TestRestTemplate restTemplate = new TestRestTemplate();

    private SimCard simCard;


    @Given("a functional sim card")
    public void aFunctionalSimCard(){
        simCard = new SimCard(false,"1255789453849037777", "hosamyoussef@gmail.com");
    }


    @Given("a broken sim card")
    public void aBrokenSimCard() {
        simCard = new SimCard(false,"8944500102198304826", "hosammohamed@gmail.com");
    }

    @When("a request to activate the sim card is submitted")
    public void aRequestToActivateTheSimCardIsSubmitted(){
        this.restTemplate.postForObject("http://localhost:8080/activate",simCard,String.class);
    }


    @Then("the sim card is activated and its state is recorded to the database")
    public void theSimCardIsActivatedAndItsStateIsRecordedToTheDatabase(){
        var simCard= this.restTemplate.getForObject("http://localhost:8080/{simCardId}", SimCard.class, 1);
        assertTrue(simCard.isActive());
    }



    @Then("the sim card fails to activate and its state is recorded to the database")
    public void theSimCardFailsToActivateAndItsStateIsRecordedToTheDatabase(){

        var simCard= this.restTemplate.getForObject("http://localhost:8080/{simCardId}", SimCard.class, 2);
        assertFalse(simCard.isActive());
    }



}