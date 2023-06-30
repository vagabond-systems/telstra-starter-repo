package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.Repositories.SimCardRepository;
import au.com.telstra.simcardactivator.models.ActuationResult;
import au.com.telstra.simcardactivator.models.SimCard;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component

public class SimCardActivation {
    private final static String activationURL = "http://localhost:8444/actuate";
    private final RestTemplate restTemplate;
    private final SimCardRepository repository;

    public SimCardActivation(RestTemplateBuilder restTemplate, SimCardRepository repository) {
        this.restTemplate = restTemplate.build();
        this.repository = repository;
    }

    public ActuationResult actuate(SimCard simCard) {
        return restTemplate.postForObject(activationURL, simCard, ActuationResult.class);


    }

    public void saveSimCard(SimCard simCard){
        repository.save(simCard);
    }

    public Optional<SimCard> getById(Long id) {
        return repository.findById(id);
    }
}
