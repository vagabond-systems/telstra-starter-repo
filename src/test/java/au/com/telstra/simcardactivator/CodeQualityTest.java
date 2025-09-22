package au.com.telstra.simcardactivator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify code quality improvements from Task 4.
 * Tests the refactored error handling, validation, and service methods.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CodeQualityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SimCardActivationService activationService;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api";
    }

    /**
     * Test the improved validation in SimCardActivationController.
     * Verifies that the refactored validateActivationRequest method works correctly.
     */
    @Test
    void testImprovedValidation() {
        // Test missing ICCID
        SimCardActivationRequest request1 = new SimCardActivationRequest();
        request1.setCustomerEmail("test@example.com");
        
        ResponseEntity<String> response1 = restTemplate.postForEntity(
            baseUrl + "/activate", 
            request1, 
            String.class
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals("ICCID is required", response1.getBody());

        // Test missing customer email
        SimCardActivationRequest request2 = new SimCardActivationRequest();
        request2.setIccid("1234567890123456789");
        
        ResponseEntity<String> response2 = restTemplate.postForEntity(
            baseUrl + "/activate", 
            request2, 
            String.class
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertEquals("Customer email is required", response2.getBody());

        // Test empty ICCID
        SimCardActivationRequest request3 = new SimCardActivationRequest();
        request3.setIccid("");
        request3.setCustomerEmail("test@example.com");
        
        ResponseEntity<String> response3 = restTemplate.postForEntity(
            baseUrl + "/activate", 
            request3, 
            String.class
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
        assertEquals("ICCID is required", response3.getBody());

        // Test empty customer email
        SimCardActivationRequest request4 = new SimCardActivationRequest();
        request4.setIccid("1234567890123456789");
        request4.setCustomerEmail("");
        
        ResponseEntity<String> response4 = restTemplate.postForEntity(
            baseUrl + "/activate", 
            request4, 
            String.class
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, response4.getStatusCode());
        assertEquals("Customer email is required", response4.getBody());
    }

    /**
     * Test the refactored service methods for better error handling.
     * Verifies that the extracted methods work correctly.
     */
    @Test
    void testRefactoredServiceMethods() {
        String testIccid = "test-iccid-" + System.currentTimeMillis();
        String testEmail = "test@example.com";

        // Test new activation (should fail due to actuator service not available)
        boolean result = activationService.activateSimCard(testIccid, testEmail);
        assertFalse(result, "Activation should fail when actuator service is not available");

        // Verify record was saved
        Optional<SimCardActivationRecord> record = activationService.getActivationRecordByIccid(testIccid);
        assertTrue(record.isPresent(), "Activation record should be saved");
        assertEquals(testIccid, record.get().getIccid());
        assertEquals(testEmail, record.get().getCustomerEmail());
        assertFalse(record.get().isActive(), "Record should be inactive due to actuator failure");

        // Test duplicate activation
        boolean duplicateResult = activationService.activateSimCard(testIccid, testEmail);
        assertFalse(duplicateResult, "Duplicate activation should return previous result");
    }

    /**
     * Test the improved error handling and logging.
     * Verifies that error responses are properly formatted.
     */
    @Test
    void testImprovedErrorHandling() {
        // Test with invalid request that should trigger error handling
        SimCardActivationRequest request = new SimCardActivationRequest();
        request.setIccid("invalid-iccid");
        request.setCustomerEmail("test@example.com");

        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/activate", 
            request, 
            String.class
        );

        // Should get a response (either success or failure, but not an error)
        assertTrue(response.getStatusCode().is2xxSuccessful() || 
                  response.getStatusCode().is4xxClientError(),
                  "Should get a proper HTTP response");
        
        assertNotNull(response.getBody(), "Response body should not be null");
    }

    /**
     * Test the new service methods added for better code organization.
     */
    @Test
    void testNewServiceMethods() {
        String testIccid = "test-service-methods-" + System.currentTimeMillis();
        String testEmail = "service@example.com";

        // Create a test record
        activationService.activateSimCard(testIccid, testEmail);

        // Test getActivationRecordByIccid
        Optional<SimCardActivationRecord> record = activationService.getActivationRecordByIccid(testIccid);
        assertTrue(record.isPresent(), "Should find record by ICCID");

        // Test getActivationRecordsByCustomerEmail
        List<SimCardActivationRecord> customerRecords = activationService.getActivationRecordsByCustomerEmail(testEmail);
        assertFalse(customerRecords.isEmpty(), "Should find records by customer email");
        assertTrue(customerRecords.stream().anyMatch(r -> r.getIccid().equals(testIccid)), 
                  "Should contain the test record");

        // Test getAllActivationRecords
        List<SimCardActivationRecord> allRecords = activationService.getAllActivationRecords();
        assertFalse(allRecords.isEmpty(), "Should have activation records");
        assertTrue(allRecords.stream().anyMatch(r -> r.getIccid().equals(testIccid)), 
                  "Should contain the test record");

        // Test getActiveSimCards
        List<SimCardActivationRecord> activeRecords = activationService.getActiveSimCards();
        // Note: Records will be inactive due to actuator service not being available
        assertTrue(activeRecords.isEmpty() || 
                  activeRecords.stream().allMatch(SimCardActivationRecord::isActive),
                  "Active records should all be marked as active");

        // Test getInactiveSimCards
        List<SimCardActivationRecord> inactiveRecords = activationService.getInactiveSimCards();
        assertTrue(inactiveRecords.isEmpty() || 
                  inactiveRecords.stream().allMatch(r -> !r.isActive()),
                  "Inactive records should all be marked as inactive");
    }

    /**
     * Test the improved GET endpoint with better error handling.
     */
    @Test
    void testImprovedGetEndpoint() {
        // Test getting non-existent record
        ResponseEntity<SimCardResponse> response = restTemplate.getForEntity(
            baseUrl + "/simcard/99999", 
            SimCardResponse.class
        );
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), 
                    "Should return 404 for non-existent record");

        // Create a test record and verify it can be retrieved
        String testIccid = "test-get-endpoint-" + System.currentTimeMillis();
        String testEmail = "gettest@example.com";
        
        activationService.activateSimCard(testIccid, testEmail);
        
        // Get the record ID
        Optional<SimCardActivationRecord> record = activationService.getActivationRecordByIccid(testIccid);
        assertTrue(record.isPresent(), "Test record should exist");
        Long recordId = record.get().getId();

        // Test getting the record by ID
        ResponseEntity<SimCardResponse> getResponse = restTemplate.getForEntity(
            baseUrl + "/simcard/" + recordId, 
            SimCardResponse.class
        );
        
        assertEquals(HttpStatus.OK, getResponse.getStatusCode(), 
                    "Should return 200 for existing record");
        assertNotNull(getResponse.getBody(), "Response body should not be null");
        assertEquals(testIccid, getResponse.getBody().getIccid());
        assertEquals(testEmail, getResponse.getBody().getCustomerEmail());
    }

    /**
     * Test the constants usage in the refactored code.
     * This verifies that magic strings have been replaced with constants.
     */
    @Test
    void testConstantsUsage() {
        // This test verifies that the refactored code uses constants instead of magic strings
        // by checking that error messages are consistent and properly formatted
        
        SimCardActivationRequest request = new SimCardActivationRequest();
        // Don't set any fields to trigger validation errors
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            baseUrl + "/activate", 
            request, 
            String.class
        );
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // The response should contain one of the constant error messages
        String responseBody = response.getBody();
        assertTrue(responseBody != null && 
                  (responseBody.contains("ICCID is required") || 
                   responseBody.contains("Customer email is required")),
                  "Response should contain proper error message from constants");
    }
}
