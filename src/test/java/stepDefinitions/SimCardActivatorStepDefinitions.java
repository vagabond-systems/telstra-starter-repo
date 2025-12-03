package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;
    private String iccid;
    ResponseEntity<Map> responseEntity;

    @Given("the SIM card with ICCID {string}")
    public void giveniccid(String iccid){
        this.iccid= iccid;
    }

    @When("I submit a activation")
    public void activation(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,String> body = new HashMap<>();
        body.put("iccid",iccid);
        body.put("customerEmail", "test@example.com");

        HttpEntity<Map<String,String>> entity = new HttpEntity<>(body,headers);
        responseEntity = restTemplate.postForEntity("http://localhost:8080/activate",entity, Map.class);
    }

    @Then("Activation is success")
    public void resultTrue(){
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertTrue((boolean) responseEntity.getBody().get("active"));
    }

    @Then("Activation is unsuccess")
    public  void resultFalse(){
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse((boolean) responseEntity.getBody().get("active"));
    }

    @Then("Get activation is {string}")
    public void checkSave(String value){
        boolean boolValue = Boolean.parseBoolean(value);
        if (boolValue){
            responseEntity = restTemplate.getForEntity("http://localhost:8080/get?id=1", Map.class);
            assertEquals(true,responseEntity.getBody().get("active"));
        }if(!boolValue){
            responseEntity = restTemplate.getForEntity("http://localhost:8080/get?id=2", Map.class);
            assertEquals(false,responseEntity.getBody().get("active"));
        }
    }
}