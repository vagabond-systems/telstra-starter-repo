package au.com.telstra.controller;

import au.com.telstra.model.SIMCard;
import au.com.telstra.pojo.SimCardResponseDTO;
import au.com.telstra.service.SimCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimCardActivateController {

    private static final String ACTIVATE_SIM_API = "http://localhost:8444/actuate";

    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private SimCardService simCardService;

    @PostMapping("/activatesimcard")
    public void addUser(@RequestBody SIMCard newSimCard) {
        System.out.println(callActivateSimCard(newSimCard));
        simCardService.addSimCard(newSimCard);
    }

    public String callActivateSimCard(SIMCard newSimCard) {
        ResponseEntity<Object> response = restTemplate.postForEntity(ACTIVATE_SIM_API, newSimCard, Object.class);
        return response.getBody().toString();
    }

    @GetMapping("/{simCardId}")
    public SimCardResponseDTO getSimCardByID(@PathVariable long simCardId) {
        SIMCard sim = simCardService.getSimCardById(simCardId);
        return new SimCardResponseDTO(sim.getIccid(),
                sim.getCustomerEmail(),
                true
                );
    }


}
