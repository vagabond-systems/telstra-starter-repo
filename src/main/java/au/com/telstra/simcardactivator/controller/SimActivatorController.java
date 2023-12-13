package au.com.telstra.simcardactivator.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.model.SimActivatorRequest;
import au.com.telstra.simcardactivator.model.SimActuateRequest;
import au.com.telstra.simcardactivator.model.SimActuateResponse;
import au.com.telstra.simcardactivator.service.RestService;

@RestController
public class SimActivatorController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RestService restService;

	@PostMapping(
			  value = "/activateSim", consumes = "application/json", produces = "application/json")
	public Boolean simActivator(@RequestBody SimActivatorRequest simActivatorRequest) throws URISyntaxException{
		
		HttpEntity<SimActivatorRequest> request = new HttpEntity<>(new SimActivatorRequest());
		URI actuateApi = new URI("http://localhost:8444/actuate");
		
		SimActuateRequest simActuateRequest = new SimActuateRequest();
		simActuateRequest.setIccid(simActivatorRequest.getIccid());
		
		ResponseEntity<SimActuateResponse> response = restService.postCall(actuateApi, SimActuateResponse.class, request);
		
		return response.getBody().getSuccess();
	}
	
}
