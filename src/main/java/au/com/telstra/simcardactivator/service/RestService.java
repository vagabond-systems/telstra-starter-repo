package au.com.telstra.simcardactivator.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.model.SimActivatorRequest;
import au.com.telstra.simcardactivator.model.SimActuateResponse;

@Service
public class RestService {

	@Autowired
	RestTemplate restTemplate;
	
	public <T> ResponseEntity<T> postCall(URI api,Class<T> clazz, HttpEntity request ) throws URISyntaxException{
		
		ResponseEntity<T> response = restTemplate
				  .exchange(api , HttpMethod.POST, request, clazz );
		
		return response;
	}
	
}
