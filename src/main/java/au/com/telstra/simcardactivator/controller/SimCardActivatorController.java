package au.com.telstra.simcardactivator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.telstra.simcardactivator.dto.RequestDto;
import au.com.telstra.simcardactivator.dto.ResponseDto;
import au.com.telstra.simcardactivator.service.SimCardActivatorService;

@RestController
@RequestMapping("/activate")
public class SimCardActivatorController {
	
	public SimCardActivatorService service;

	public SimCardActivatorController(SimCardActivatorService service) {
		super();
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<String> postActivateRequest(@RequestBody RequestDto dto){
		if(dto.getIccid()!=null && dto.getCustomerEmail()!=null) {
			String response= service.request(dto);
			return new ResponseEntity<String>(response,HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Invalid Request",HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseDto> getActivationDetails(@PathVariable Long id){
		ResponseDto dto= service.getDetails(id);
		if(dto==null) {
			return new ResponseEntity<ResponseDto>(dto,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ResponseDto>(dto,HttpStatus.OK);
	}
	
	
	
}
