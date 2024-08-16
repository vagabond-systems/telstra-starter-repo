package stepDefinitions;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import au.com.telstra.simcardactivator.dto.RequestDto;
import au.com.telstra.simcardactivator.dto.ResponseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {RequestDto.class,ResponseDto.class}, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;
    	@Autowired
        private RequestDto simCard;

        @Given("a functional sim card")
        public void aFunctionalSimCard() {
            simCard = new RequestDto("1255789453849037777","horatio.yakima@groovemail.com");
        }

        @Given("a broken sim card")
        public void aBrokenSimCard() {
        	simCard = new RequestDto("8944500102198304826","notorious.criminal@gonepostal.com");
        }

        @When("a request to activate the sim card is submitted")
        public void aRequestToActivateTheSimCardIsSubmitted() {
            this.restTemplate.postForObject("http://localhost:8080/activate", simCard, String.class);
        }

        @Then("the sim card is activated and its state is recorded to the database")
        public void theSimCardIsActivatedAndItsStateIsRecordedToTheDatabase() {
            ResponseDto simCard = this.restTemplate.getForObject("http://localhost:8080/activate/{id}", ResponseDto.class,1);
            Assert.assertTrue(simCard.isActive());
        }

        @Then("the sim card fails to activate and its state is recorded to the database")
        public void theSimCardFailsToActivateAndItsStateIsRecordedToTheDatabase() {
            ResponseDto simCard = this.restTemplate.getForObject("http://localhost:8080/activate/{id}", ResponseDto.class,2);
            Assert.assertTrue(simCard.isActive());
            
        }

    }

