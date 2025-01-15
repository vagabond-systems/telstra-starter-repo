package stepDefinitions;


import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;


import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {


    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String baseUrl = "http://localhost:8080";
    private ResponseEntity<Map> response;

    private String iccid;
    private boolean activationResult;

    @Given("the SIM card ICCID {string}")
    public void theSimCardICCID(String iccid) {
        this.iccid = iccid;
    }

    @When("I activate the SIM card")
    public void iActivateTheSimCard() {
        HttpEntity<String> request = new HttpEntity<>(iccid);
        response = restTemplate.postForEntity(baseUrl + "/activate", request, Map.class);
    }

    @Then("the activation should be successful")
    public void theActivationShouldBeSuccessful() {
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Activation successful", response.getBody().get("message"));
    }

    @Then("the activation should fail")
    public void theActivationShouldFail() {
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Activation failed", response.getBody().get("message"));
    }

    @Then("the SIM card status should be:")
    public void theSIMCardStatusShouldBe(Map<String, String> expectedStatus) {
        long simCardId = response.getBody().get("simCardId") != null
                ? Long.parseLong(response.getBody().get("simCardId").toString())
                : -1;

        if (simCardId != -1) {
            Map<String, Object> simCardResponse = restTemplate.getForObject(baseUrl + "/query?simCardId=" + simCardId, Map.class);

            assertEquals(expectedStatus.get("iccid"), simCardResponse.get("iccid"));
            assertEquals(expectedStatus.get("customerEmail"), simCardResponse.get("customerEmail"));
            assertEquals(Boolean.valueOf(expectedStatus.get("active")), simCardResponse.get("active"));
        }
    }

    public boolean isActivationResult() {
        return activationResult;
    }

    public void setActivationResult(boolean activationResult) {
        this.activationResult = activationResult;
    }
}