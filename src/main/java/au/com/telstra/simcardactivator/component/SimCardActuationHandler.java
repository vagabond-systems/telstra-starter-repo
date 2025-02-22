package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.ActuationResult;
import au.com.telstra.simcardactivator.foundation.SimCard;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SimCardActuationHandler {
    private final RestTemplate restTemplate;
    private final String incentiveApiUrl;

    public SimCardActuationHandler(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
        this.incentiveApiUrl = "http://localhost:8444/actuate";
    }

    public ActuationResult actuate(SimCard simCard) {
        return restTemplate.postForObject(incentiveApiUrl, simCard, ActuationResult.class);
    }

}
