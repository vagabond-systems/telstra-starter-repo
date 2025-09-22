package au.com.telstra.simcardactivator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for SIM card activation operations.
 * Provides endpoints for activating SIM cards and retrieving activation records.
 */
@RestController
@RequestMapping("/api")
public class SimCardActivationController {
    
    private static final Logger logger = LoggerFactory.getLogger(SimCardActivationController.class);
    private static final String ICCID_REQUIRED_MESSAGE = "ICCID is required";
    private static final String EMAIL_REQUIRED_MESSAGE = "Customer email is required";
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String FAILURE_MESSAGE = "FAILURE";
    private static final String ACTIVATION_RESULT_FORMAT = "Activation %s for ICCID: %s";
    private static final String ERROR_PROCESSING_MESSAGE = "Error processing activation request: ";
    
    @Autowired
    private SimCardActivationService activationService;
    
    /**
     * Activates a SIM card.
     * 
     * @param request the activation request containing ICCID and customer email
     * @return response indicating success or failure
     */
    @PostMapping("/activate")
    public ResponseEntity<String> activateSimCard(@RequestBody SimCardActivationRequest request) {
        try {
            logger.info("Received activation request: {}", request);
            
            // Validate request
            ResponseEntity<String> validationError = validateActivationRequest(request);
            if (validationError != null) {
                return validationError;
            }
            
            // Call the activation service
            boolean success = activationService.activateSimCard(request.getIccid(), request.getCustomerEmail());
            
            // Return the result
            String result = success ? SUCCESS_MESSAGE : FAILURE_MESSAGE;
            logger.info("SIM card activation result for ICCID {}: {}", request.getIccid(), result);
            
            return ResponseEntity.ok(String.format(ACTIVATION_RESULT_FORMAT, result, request.getIccid()));
            
        } catch (Exception e) {
            logger.error("Error processing activation request: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body(ERROR_PROCESSING_MESSAGE + e.getMessage());
        }
    }
    
    /**
     * Validates the activation request.
     * 
     * @param request the activation request to validate
     * @return ResponseEntity with error if validation fails, null if valid
     */
    private ResponseEntity<String> validateActivationRequest(SimCardActivationRequest request) {
        if (request == null) {
            logger.warn("Invalid request: Request body is null");
            return ResponseEntity.badRequest().body("Request body is required");
        }
        
        if (request.getIccid() == null || request.getIccid().trim().isEmpty()) {
            logger.warn("Invalid request: ICCID is required");
            return ResponseEntity.badRequest().body(ICCID_REQUIRED_MESSAGE);
        }
        
        if (request.getCustomerEmail() == null || request.getCustomerEmail().trim().isEmpty()) {
            logger.warn("Invalid request: Customer email is required");
            return ResponseEntity.badRequest().body(EMAIL_REQUIRED_MESSAGE);
        }
        
        return null; // Validation passed
    }
    
    /**
     * Retrieves all activation records.
     * 
     * @return list of all activation records
     */
    @GetMapping("/activations")
    public ResponseEntity<List<SimCardActivationRecord>> getAllActivations() {
        try {
            logger.info("Retrieving all activation records");
            List<SimCardActivationRecord> records = activationService.getAllActivationRecords();
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            logger.error("Error retrieving all activations: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Retrieves an activation record by ICCID.
     * 
     * @param iccid the SIM card ICCID
     * @return the activation record if found
     */
    @GetMapping("/activations/{iccid}")
    public ResponseEntity<SimCardActivationRecord> getActivationByIccid(@PathVariable String iccid) {
        try {
            logger.info("Retrieving activation record for ICCID: {}", iccid);
            Optional<SimCardActivationRecord> record = activationService.getActivationRecordByIccid(iccid);
            
            if (record.isPresent()) {
                return ResponseEntity.ok(record.get());
            } else {
                logger.warn("Activation record not found for ICCID: {}", iccid);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error retrieving activation record for ICCID {}: {}", iccid, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Retrieves a SIM card record by ID.
     * 
     * @param simCardId the SIM card record ID
     * @return the SIM card response if found
     */
    @GetMapping("/simcard/{simCardId}")
    public ResponseEntity<SimCardResponse> getSimCardById(@PathVariable Long simCardId) {
        try {
            logger.info("Retrieving SIM card record for ID: {}", simCardId);
            Optional<SimCardActivationRecord> record = activationService.getActivationRecordById(simCardId);
            
            if (record.isPresent()) {
                SimCardActivationRecord activationRecord = record.get();
                SimCardResponse response = new SimCardResponse(
                    activationRecord.getIccid(),
                    activationRecord.getCustomerEmail(),
                    activationRecord.isActive()
                );
                return ResponseEntity.ok(response);
            } else {
                logger.warn("Activation record not found for ID: {}", simCardId);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error retrieving SIM card record for ID {}: {}", simCardId, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
