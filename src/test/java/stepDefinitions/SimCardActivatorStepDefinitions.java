package stepDefinitions;

import au.com.telstra.simcardactivator.Entity.ActivationPayLoad;
import au.com.telstra.simcardactivator.Entity.Customer;
import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    Logger logger= LoggerFactory.getLogger(SimCardActivatorStepDefinitions.class);
    @Autowired
    private TestRestTemplate restTemplate;






        private String baseUrl = "http://localhost:8080"; // Adjust as necessary
        private ResponseEntity<Customer> lastResponse;

        @Given("the SIM card actuator is running")
        public void the_sim_card_actuator_is_running() {
            // Optionally check if the actuator service is healthy
            // This could be a simple GET request to a health endpoint if available
        }

        @When("I submit an activation request with ICCID {string}")
        public void i_submit_an_activation_request_with_iccid(String iccid) {
            String url = baseUrl + "/ActivateSIM";
            ActivationPayLoad payload = new ActivationPayLoad(iccid, "customer@example.com"); // Use a valid email or parameterize it
            lastResponse = restTemplate.postForEntity(url, payload, Customer.class);
        }

        @Then("the activation should be successful")
        public void the_activation_should_be_successful() {
            // Check if lastResponse indicates success (HTTP status 200)
            assert lastResponse.getStatusCode().is2xxSuccessful();
            assert lastResponse.getBody() != null; // Ensure a customer object is returned
            assert lastResponse.getBody().getIccid().equals("1255789453849037777"); // Verify correct ICCID
        }

        @Then("the activation should fail")
        public void the_activation_should_fail() {
            // Check if lastResponse indicates failure (HTTP status 4xx or 5xx)
            assert lastResponse.getStatusCode().isError(); // This checks for any error response
        }

        @Then("I can verify the activation status from the database")
        public void i_can_verify_the_activation_status_from_the_database() {
            String iccid = "8944500102198304826"; // Use the successful ICCID for verification
            String url = baseUrl + "/getCustomer/" + iccid;
            ResponseEntity<Customer> customerResponse = restTemplate.getForEntity(url, Customer.class);

            // Validate that the customer is present and activated in the database
            assert customerResponse.getStatusCode().is2xxSuccessful();
            assert customerResponse.getBody() != null; // Ensure a customer object is returned
            assert customerResponse.getBody().getIccid().equals(iccid); // Validate ICCID matches
        }
    }
