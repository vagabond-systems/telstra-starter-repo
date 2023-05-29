package au.com.telstra.controller;

import au.com.telstra.model.SIMCard;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SimCardActivateController {

    private static final String ACTIVATE_SIM_API = "http://localhost:8444/actuate";

     RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/activatesimcard")
    public void addUser(@RequestBody SIMCard newSimCard){
        System.out.println(callActivateSimCard(newSimCard));
    }

    public String callActivateSimCard(SIMCard newSimCard){
        ResponseEntity<Object> response = restTemplate.postForEntity(ACTIVATE_SIM_API,newSimCard,Object.class);
        return response.getBody().toString();
    }

}
