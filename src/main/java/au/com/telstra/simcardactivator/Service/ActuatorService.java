package au.com.telstra.simcardactivator.Service;

import au.com.telstra.simcardactivator.Entity.ActuatorResponse;
import au.com.telstra.simcardactivator.Entity.Customer;
import au.com.telstra.simcardactivator.Repository.SIMActivatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service

public class ActuatorService {
    private static final String ActuatorURL="http://localhost:8444/actuate";
    private static final Logger log= LoggerFactory.getLogger(ActuatorService.class);
    @Autowired
    private SIMActivatorRepository simActivatorRepository;

    public Customer activateSIM(String iccid,String customerEmail) {
        RestTemplate restTemplate=new RestTemplate();

//        try{

            Map<String,String> payload=new HashMap<String,String>();
            payload.put("ICCID",iccid);
            boolean success=restTemplate.postForObject(ActuatorURL,payload, ActuatorResponse.class).isSuccess();
            Customer customer=new Customer(iccid,customerEmail,success);
            return simActivatorRepository.save(customer);

//            return actuatorResponse!=null && actuatorResponse.isSuccess();

//        catch(Exception e){
//
//
//        }


    }

    public Customer getCustomerById(String iccid) {
        return simActivatorRepository.getByIccid(iccid);
    }
}
