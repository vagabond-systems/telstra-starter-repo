package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for handling SIM card activation operations.
 * Provides methods to activate SIM cards, retrieve activation records,
 * and manage the persistence layer for activation data.
 */
@Service
public class SimCardActivationService {
    
    private static final Logger logger = LoggerFactory.getLogger(SimCardActivationService.class);
    private static final String ERROR_PREFIX = "Error: ";
    private static final String NULL_RESPONSE = "null";
    
    @Value("${actuator.service.url}")
    private String actuatorUrl;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private SimCardActivationRepository repository;
    
    /**
     * Activates a SIM card by calling the external actuator service.
     * 
     * @param iccid the SIM card ICCID
     * @param customerEmail the customer email address
     * @return true if activation was successful, false otherwise
     */
    public boolean activateSimCard(String iccid, String customerEmail) {
        logger.info("Starting SIM card activation for ICCID: {} and customer: {}", iccid, customerEmail);
        
        try {
            // Check if this ICCID has been activated before
            if (repository.existsByIccid(iccid)) {
                return handleExistingActivation(iccid);
            }
            
            // Attempt new activation
            return performNewActivation(iccid, customerEmail);
            
        } catch (Exception e) {
            logger.error("Error calling actuator service for ICCID {}: {}", iccid, e.getMessage(), e);
            saveFailedActivation(iccid, customerEmail, e.getMessage());
            return false;
        }
    }
    
    /**
     * Handles the case where an ICCID has already been activated.
     * 
     * @param iccid the SIM card ICCID
     * @return the previous activation result
     */
    private boolean handleExistingActivation(String iccid) {
        logger.warn("ICCID {} has already been activated", iccid);
        Optional<SimCardActivationRecord> existingRecord = repository.findByIccid(iccid);
        if (existingRecord.isPresent()) {
            SimCardActivationRecord record = existingRecord.get();
            logger.info("Previous activation result for ICCID {}: {}", iccid, record.isActive());
            return record.isActive();
        }
        return false;
    }
    
    /**
     * Performs a new SIM card activation by calling the actuator service.
     * 
     * @param iccid the SIM card ICCID
     * @param customerEmail the customer email address
     * @return true if activation was successful, false otherwise
     */
    private boolean performNewActivation(String iccid, String customerEmail) {
        ActuatorRequest actuatorRequest = new ActuatorRequest(iccid);
        HttpEntity<ActuatorRequest> requestEntity = createHttpEntity(actuatorRequest);
        
        logger.debug("Calling actuator service at: {}", actuatorUrl);
        
        ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(
            actuatorUrl, 
            requestEntity, 
            ActuatorResponse.class
        );
        
        boolean success = isActivationSuccessful(response);
        String responseBody = getResponseBody(response);
        
        logger.info("Actuator response for ICCID {}: {}", iccid, responseBody);
        
        saveActivationRecord(iccid, customerEmail, success, responseBody);
        
        logger.info("SIM card activation {} for ICCID: {}", success ? "SUCCESS" : "FAILED", iccid);
        
        return success;
    }
    
    /**
     * Creates an HTTP entity with proper headers for the actuator request.
     * 
     * @param actuatorRequest the request object
     * @return the HTTP entity
     */
    private HttpEntity<ActuatorRequest> createHttpEntity(ActuatorRequest actuatorRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(actuatorRequest, headers);
    }
    
    /**
     * Determines if the activation was successful based on the response.
     * 
     * @param response the HTTP response from the actuator
     * @return true if successful, false otherwise
     */
    private boolean isActivationSuccessful(ResponseEntity<ActuatorResponse> response) {
        return response.getBody() != null && response.getBody().isSuccess();
    }
    
    /**
     * Extracts the response body as a string.
     * 
     * @param response the HTTP response
     * @return the response body as string
     */
    private String getResponseBody(ResponseEntity<ActuatorResponse> response) {
        return response.getBody() != null ? response.getBody().toString() : NULL_RESPONSE;
    }
    
    /**
     * Saves an activation record to the database.
     * 
     * @param iccid the SIM card ICCID
     * @param customerEmail the customer email
     * @param success whether activation was successful
     * @param responseBody the response from actuator
     */
    private void saveActivationRecord(String iccid, String customerEmail, boolean success, String responseBody) {
        SimCardActivationRecord record = new SimCardActivationRecord(iccid, customerEmail, success);
        record.setActuatorResponse(responseBody);
        repository.save(record);
    }
    
    /**
     * Saves a failed activation record to the database.
     * 
     * @param iccid the SIM card ICCID
     * @param customerEmail the customer email
     * @param errorMessage the error message
     */
    private void saveFailedActivation(String iccid, String customerEmail, String errorMessage) {
        SimCardActivationRecord record = new SimCardActivationRecord(iccid, customerEmail, false);
        record.setActuatorResponse(ERROR_PREFIX + errorMessage);
        repository.save(record);
    }
    
    /**
     * Retrieves all activation records.
     * 
     * @return list of all activation records
     */
    public List<SimCardActivationRecord> getAllActivationRecords() {
        logger.debug("Retrieving all activation records");
        return repository.findAll();
    }
    
    /**
     * Retrieves an activation record by ICCID.
     * 
     * @param iccid the SIM card ICCID
     * @return optional containing the activation record if found
     */
    public Optional<SimCardActivationRecord> getActivationRecordByIccid(String iccid) {
        logger.debug("Retrieving activation record for ICCID: {}", iccid);
        return repository.findByIccid(iccid);
    }
    
    /**
     * Retrieves an activation record by ID.
     * 
     * @param id the record ID
     * @return optional containing the activation record if found
     */
    public Optional<SimCardActivationRecord> getActivationRecordById(Long id) {
        logger.debug("Retrieving activation record for ID: {}", id);
        return repository.findById(id);
    }
    
    /**
     * Retrieves activation records by customer email.
     * 
     * @param customerEmail the customer email address
     * @return list of activation records for the customer
     */
    public List<SimCardActivationRecord> getActivationRecordsByCustomerEmail(String customerEmail) {
        logger.debug("Retrieving activation records for customer: {}", customerEmail);
        return repository.findByCustomerEmail(customerEmail);
    }
    
    /**
     * Retrieves all active SIM cards.
     * 
     * @return list of active SIM card records
     */
    public List<SimCardActivationRecord> getActiveSimCards() {
        logger.debug("Retrieving all active SIM cards");
        return repository.findByActiveTrue();
    }
    
    /**
     * Retrieves all inactive SIM cards.
     * 
     * @return list of inactive SIM card records
     */
    public List<SimCardActivationRecord> getInactiveSimCards() {
        logger.debug("Retrieving all inactive SIM cards");
        return repository.findByActiveFalse();
    }
}
