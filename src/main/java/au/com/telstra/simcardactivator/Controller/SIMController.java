package au.com.telstra.simcardactivator.Controller;

import au.com.telstra.simcardactivator.Entity.ActivationPayLoad;
import au.com.telstra.simcardactivator.Entity.ActuatorResponse;
import au.com.telstra.simcardactivator.Entity.Customer;
import au.com.telstra.simcardactivator.Service.ActuatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SIMController {
//    @Autowired
   private ActuatorService actuatorService;
   public SIMController(ActuatorService actuatorService){
       this.actuatorService=actuatorService;
   }
    @PostMapping("/ActivateSIM")
    public ResponseEntity<Customer> activateSIM(@RequestBody ActivationPayLoad activationPayLoad){
        Customer customer=actuatorService.activateSIM(activationPayLoad.getIccid(),activationPayLoad.getCustomerEmail());
        return ResponseEntity.ok(customer);


    }
    @GetMapping("/getCustomer/{iccid}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String iccid){
        Customer customer= actuatorService.getCustomerById(iccid);
        return ResponseEntity.ok(customer);

    }


}
