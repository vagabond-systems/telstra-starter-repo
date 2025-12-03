package au.com.telstra.simcardactivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ActivationService {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private ActivationRepository activationRepository;

    @Autowired
    ActivationService(ActivationRepository activationRepository){
        this.activationRepository = activationRepository;
    }

    public ActivationEntity activateService(String iccid, String email) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> request = new HashMap<>();
        request.put("iccid",iccid);

        HttpHeaders httpHeader= new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,String>> httpEntity = new HttpEntity<>(request,httpHeader);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity("http://localhost:8444/actuate",httpEntity, Map.class);
        ActivationEntity activationEntity = new ActivationEntity();
        activationEntity.setIccid(iccid);
        activationEntity.setCustomerEmail(email);

        Map body = responseEntity.getBody();

        if(body != null){
            if(body.containsKey("success")){
                activationEntity.setActive(Boolean.parseBoolean((String) body.get("success")));
            } else {
                activationEntity.setActive(false);
            }
        }
        if(body != null) {
            logger.info(body.toString());
        }
        return activationRepository.save(activationEntity);
    }
}
