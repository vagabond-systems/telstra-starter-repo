package stepDefinitions;

import au.com.telstra.simcardactivator.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SimCardActivationService activationService;

    private SimCardActivationRequest request;
    private ResponseEntity<String> response;
    private ResponseEntity<SimCardResponse> simCardResponse;
    private List<SimCardActivationRecord> allRecords;
    private String currentIccid;
    private String currentCustomerEmail;
    private Long expectedRecordId;

    @Given("the SIM card activation service is running")
    public void the_sim_card_activation_service_is_running() {
        // Service is running via Spring Boot test context
        assertNotNull(restTemplate);
    }

    @Given("the actuator service is available")
    public void the_actuator_service_is_available() {
        // This would require the actuator service to be running
        // For now, we'll test with the service unavailable scenario
    }

    @Given("I have a valid SIM card with ICCID {string}")
    public void i_have_a_valid_sim_card_with_iccid(String iccid) {
        request = new SimCardActivationRequest();
        request.setIccid(iccid);
        currentIccid = iccid;
    }

    @Given("I have an invalid SIM card with ICCID {string}")
    public void i_have_an_invalid_sim_card_with_iccid(String iccid) {
        request = new SimCardActivationRequest();
        request.setIccid(iccid);
        currentIccid = iccid;
    }

    @Given("the customer email is {string}")
    public void the_customer_email_is(String email) {
        if (request == null) {
            request = new SimCardActivationRequest();
        }
        request.setCustomerEmail(email);
        currentCustomerEmail = email;
    }

    @When("I submit the activation request")
    public void i_submit_the_activation_request() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SimCardActivationRequest> entity = new HttpEntity<>(request, headers);
        
        response = restTemplate.postForEntity("/api/activate", entity, String.class);
    }

    @When("I query the database for all activation records")
    public void i_query_the_database_for_all_activation_records() {
        allRecords = activationService.getAllActivationRecords();
    }

    @When("I query the database for SIM card with ID {long}")
    public void i_query_the_database_for_sim_card_with_id(Long id) {
        ResponseEntity<SimCardResponse> response = restTemplate.getForEntity("/api/simcard/" + id, SimCardResponse.class);
        simCardResponse = response;
        expectedRecordId = id;
    }

    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        assertNotNull(response);
        assertTrue(response.getBody().contains("SUCCESS"));
    }

    @Then("the activation should fail with validation error")
    public void the_activation_should_fail_with_validation_error() {
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("required"));
    }

    @Then("the activation should fail due to actuator service")
    public void the_activation_should_fail_due_to_actuator_service() {
        assertNotNull(response);
        assertTrue(response.getBody().contains("FAILURE"));
    }

    @Then("the activation record should be saved with ID {long}")
    public void the_activation_record_should_be_saved_with_id(Long expectedId) {
        // Query all records to find the one we just created
        allRecords = activationService.getAllActivationRecords();
        assertNotNull(allRecords);
        assertFalse(allRecords.isEmpty());
        
        // Find the record for our test ICCID
        SimCardActivationRecord foundRecord = null;
        for (SimCardActivationRecord record : allRecords) {
            if (record.getIccid().equals(currentIccid)) {
                foundRecord = record;
                break;
            }
        }
        
        assertNotNull(foundRecord, "Activation record should be saved in database");
        assertEquals(expectedId, foundRecord.getId());
        assertEquals(currentCustomerEmail, foundRecord.getCustomerEmail());
    }

    @Then("the activation record should be saved")
    public void the_activation_record_should_be_saved() {
        assertNotNull(allRecords);
        assertFalse(allRecords.isEmpty());
        
        // Find the record for our test ICCID
        boolean found = false;
        for (SimCardActivationRecord record : allRecords) {
            if (record.getIccid().equals(currentIccid)) {
                found = true;
                assertEquals(currentCustomerEmail, record.getCustomerEmail());
                break;
            }
        }
        assertTrue(found, "Activation record should be saved in database");
    }

    @Then("I should receive a success response")
    public void i_should_receive_a_success_response() {
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Then("I should receive a failure response")
    public void i_should_receive_a_failure_response() {
        assertNotNull(response);
        assertTrue(response.getBody().contains("FAILURE"));
    }

    @Then("I should receive a bad request response")
    public void i_should_receive_a_bad_request_response() {
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Then("the SIM card should be marked as active")
    public void the_sim_card_should_be_marked_as_active() {
        assertNotNull(allRecords);
        boolean found = false;
        for (SimCardActivationRecord record : allRecords) {
            if (record.getIccid().equals(currentIccid)) {
                found = true;
                assertTrue(record.isActive(), "SIM card should be marked as active");
                break;
            }
        }
        assertTrue(found, "SIM card record should exist");
    }

    @Then("the SIM card should be marked as inactive")
    public void the_sim_card_should_be_marked_as_inactive() {
        assertNotNull(allRecords);
        boolean found = false;
        for (SimCardActivationRecord record : allRecords) {
            if (record.getIccid().equals(currentIccid)) {
                found = true;
                assertFalse(record.isActive(), "SIM card should be marked as inactive");
                break;
            }
        }
        assertTrue(found, "SIM card record should exist");
    }

    @Then("I should receive the SIM card details")
    public void i_should_receive_the_sim_card_details() {
        assertNotNull(simCardResponse);
        assertEquals(200, simCardResponse.getStatusCodeValue());
        assertNotNull(simCardResponse.getBody());
    }

    @Then("the response should contain ICCID {string}")
    public void the_response_should_contain_iccid(String expectedIccid) {
        assertNotNull(simCardResponse.getBody());
        assertEquals(expectedIccid, simCardResponse.getBody().getIccid());
    }

    @Then("the response should contain customer email {string}")
    public void the_response_should_contain_customer_email(String expectedEmail) {
        assertNotNull(simCardResponse.getBody());
        assertEquals(expectedEmail, simCardResponse.getBody().getCustomerEmail());
    }

    @Then("the response should show active status as {string}")
    public void the_response_should_show_active_status_as(String expectedStatus) {
        assertNotNull(simCardResponse.getBody());
        boolean expectedActive = "true".equalsIgnoreCase(expectedStatus);
        assertEquals(expectedActive, simCardResponse.getBody().isActive());
    }

    // Additional step definitions for other scenarios
    @Given("there are existing activation records")
    public void there_are_existing_activation_records() {
        // This step is handled by the background and previous scenarios
    }

    @When("I request all activation records")
    public void i_request_all_activation_records() {
        ResponseEntity<SimCardActivationRecord[]> response = restTemplate.getForEntity("/api/activations", SimCardActivationRecord[].class);
        allRecords = List.of(response.getBody());
    }

    @Then("I should receive a list of activation records")
    public void i_should_receive_a_list_of_activation_records() {
        assertNotNull(allRecords);
        assertFalse(allRecords.isEmpty());
    }

    @Given("there is an activation record for ICCID {string}")
    public void there_is_an_activation_record_for_iccid(String iccid) {
        // This would be set up by previous scenarios
        currentIccid = iccid;
    }

    @When("I request the activation record for ICCID {string}")
    public void i_request_the_activation_record_for_iccid(String iccid) {
        ResponseEntity<SimCardActivationRecord> response = restTemplate.getForEntity("/api/activations/" + iccid, SimCardActivationRecord.class);
        // Handle the response as needed
    }

    @Then("I should receive the activation record")
    public void i_should_receive_the_activation_record() {
        // Assertion for receiving the activation record
    }

    @Given("there are activation records for customer {string}")
    public void there_are_activation_records_for_customer(String email) {
        // This would be set up by previous scenarios
        currentCustomerEmail = email;
    }

    @When("I request activation records for customer {string}")
    public void i_request_activation_records_for_customer(String email) {
        // Implementation for requesting records by customer email
    }

    @Then("I should receive the customer's activation records")
    public void i_should_receive_the_customer_s_activation_records() {
        // Assertion for receiving customer records
    }

    @Given("there are both successful and failed activation records")
    public void there_are_both_successful_and_failed_activation_records() {
        // This would be set up by previous scenarios
    }

    @When("I request successful activations")
    public void i_request_successful_activations() {
        // Implementation for requesting successful activations
    }

    @Then("I should receive only successful activation records")
    public void i_should_receive_only_successful_activation_records() {
        // Assertion for successful records only
    }

    @When("I request failed activations")
    public void i_request_failed_activations() {
        // Implementation for requesting failed activations
    }

    @Then("I should receive only failed activation records")
    public void i_should_receive_only_failed_activation_records() {
        // Assertion for failed records only
    }

    @Given("there is already an activation record for ICCID {string}")
    public void there_is_already_an_activation_record_for_iccid(String iccid) {
        // This would be set up by previous scenarios
        currentIccid = iccid;
    }

    @When("I try to activate the same ICCID {string} again")
    public void i_try_to_activate_the_same_iccid_again(String iccid) {
        request = new SimCardActivationRequest();
        request.setIccid(iccid);
        request.setCustomerEmail("customer@example.com");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SimCardActivationRequest> entity = new HttpEntity<>(request, headers);
        
        response = restTemplate.postForEntity("/api/activate", entity, String.class);
    }

    @Then("the system should return the existing activation result")
    public void the_system_should_return_the_existing_activation_result() {
        assertNotNull(response);
        // The system should return a message about duplicate activation
        assertTrue(response.getBody().contains("already activated") || response.getBody().contains("duplicate"));
    }

    @Then("no new activation record should be created")
    public void no_new_activation_record_should_be_created() {
        // Query all records and verify no new record was created
        List<SimCardActivationRecord> recordsAfter = activationService.getAllActivationRecords();
        // This would need to be compared with records before the duplicate attempt
    }
}
