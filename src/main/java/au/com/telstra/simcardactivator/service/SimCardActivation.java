package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.models.ActuationResult;
import au.com.telstra.simcardactivator.models.SimCard;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



@Component

public class SimCardActivation {
    private final static String activationURL = "http://localhost:8444/actuate";
    private final RestTemplate restTemplate;

    public SimCardActivation(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate.build();
    }

    public ActuationResult actuate(SimCard simCard) {
        return restTemplate.postForObject(activationURL, simCard, ActuationResult.class);
    }
}
