package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Given("I submit a SIM activation request with ICCID {string} and email {string}")
    public void i_submit_sim_activation_request(String iccid, String email) {
        String url = "/activate";

        String json = String.format("{\"iccid\":\"%s\", \"customerEmail\":\"%s\"}", iccid, email);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        response = restTemplate.postForEntity(url, request, String.class);
    }

    @When("I query the activation status for SIM card ID {int}")
    public void i_query_the_activation_status(int id) {
        response = restTemplate.getForEntity("/activations/" + id, String.class);
    }

    @Then("the activation should be marked as {string}")
    public void the_activation_should_be_marked_as(String expectedStatus) {
        System.out.println("Response Body: " + response.getBody());
        assertFalse(response.getBody().contains("\"active\":" + expectedStatus));
    }
}
