package au.com.telstra.simcardactivator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.dto.ActuatorDto;
import au.com.telstra.simcardactivator.dto.ActuatorResponseDto;
import au.com.telstra.simcardactivator.dto.RequestDto;
import au.com.telstra.simcardactivator.dto.ResponseDto;
import au.com.telstra.simcardactivator.entity.SimCardActivator;
import au.com.telstra.simcardactivator.repository.SimCardActivatorRepository;
@Service
public class SimCardActivatorService {

	public RestTemplate restTemplate;
	public SimCardActivatorRepository repository;

	public SimCardActivatorService(RestTemplate restTemplate, SimCardActivatorRepository
			repository) {
		super();
		this.restTemplate = restTemplate;
		this.repository=repository;
	}
	
	public String request(RequestDto dto) {
		ActuatorDto actuatorDto=new ActuatorDto(dto.getIccid());
		ActuatorResponseDto responseDto= restTemplate.postForEntity("http://localhost:8444/actuate", actuatorDto, ActuatorResponseDto.class).getBody();
		if(responseDto!=null) {
		if(responseDto.isSuccess()) {
			SimCardActivator activator=new SimCardActivator(dto.getIccid(),dto.getCustomerEmail(), responseDto.isSuccess());
			repository.save(activator);
			return "Successful";
		}
		else {
			SimCardActivator activator=new SimCardActivator(dto.getIccid(),dto.getCustomerEmail(), responseDto.isSuccess());
			repository.save(activator);
			return "Unsuccessful";
		}
		}
		return null;
	}
	
	public ResponseDto getDetails(Long id) {
		if(repository.findById(id).isPresent()) {
		SimCardActivator activator= repository.findById(id).get();
		ResponseDto dto=new ResponseDto(activator.getIccid(), activator.getCustomerEmail(), activator.isActive());
		return dto;
		}
		return null;
	}
	
}
