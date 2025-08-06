package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.model.ActivationRequest;
import au.com.telstra.simcardactivator.model.ActivationResponse;
import au.com.telstra.simcardactivator.model.ActuatorRequest;
import au.com.telstra.simcardactivator.model.ActuatorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/activate")
public class SimCardActivatorController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @PostMapping
    public ResponseEntity<ActivationResponse> activateSimCard(@RequestBody ActivationRequest request) {
        try {
            System.out.println("Received activation request for ICCID: " + request.getIccid() + 
                             ", Customer Email: " + request.getCustomerEmail());

            // Create request for actuator service
            ActuatorRequest actuatorRequest = new ActuatorRequest(request.getIccid());

            // Call actuator service
            ResponseEntity<ActuatorResponse> actuatorResponse = restTemplate.postForEntity(
                ACTUATOR_URL, actuatorRequest, ActuatorResponse.class);

            boolean success = actuatorResponse.getBody().isSuccess();
            
            // Print result
            if (success) {
                System.out.println("SIM card activation SUCCESSFUL for ICCID: " + request.getIccid());
            } else {
                System.out.println("SIM card activation FAILED for ICCID: " + request.getIccid());
            }

            // Return response
            ActivationResponse response = new ActivationResponse(success);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error during SIM card activation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ActivationResponse(false));
        }
    }
}
