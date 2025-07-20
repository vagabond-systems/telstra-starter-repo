package au.com.telstra.simcardactivator;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@RestController
public class SimActivationController {

    private final RestTemplate restTemplate;
    private final SimActivationRecordRepository repository;

    public SimActivationController(SimActivationRecordRepository repository) {
        this.restTemplate = new RestTemplate();
        this.repository = repository;
    }

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("SIM Activation Microservice is running!");
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimActivationRequest request) {
        Map<String, String> actuatorRequest = new HashMap<>();
        actuatorRequest.put("iccid", request.getIccid());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(actuatorRequest, headers);

        boolean success;

        try {
            ResponseEntity<ActuatorResponse> response = restTemplate.postForEntity(
                    "http://localhost:8444/actuate", entity, ActuatorResponse.class
            );
            success = Boolean.TRUE.equals(response.getBody().isSuccess());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Activation failed: " + e.getMessage());
        }

        // Save activation result to DB
        SimActivationRecord record = new SimActivationRecord();
        record.setIccid(request.getIccid());
        record.setCustomerEmail(request.getCustomerEmail());
        record.setActive(success);
        record.setTimestamp(LocalDateTime.now());

        SimActivationRecord saved = repository.save(record);

        return ResponseEntity.ok("Activation success: " + success + " | ID: " + saved.getId());
    }

    @GetMapping("/activation")
    public ResponseEntity<?> getActivation(@RequestParam Long simCardId) {
        return repository.findById(simCardId)
                .map(record -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("iccid", record.getIccid());
                    response.put("customerEmail", record.getCustomerEmail());
                    response.put("active", record.isActive());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "SIM card record not found")));
    }
}
