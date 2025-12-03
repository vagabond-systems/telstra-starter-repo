package au.com.telstra.simcardactivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivationController {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    private ActivationService activationService;
    private ActivationRepository activationRepository;

    @Autowired
    ActivationController(ActivationService activationService, ActivationRepository activationRepository){
        this.activationService = activationService;
        this.activationRepository = activationRepository;
    }
    @PostMapping("/activate")
    public ActivationEntity activate(@RequestBody ActivationRequest request){
        ActivationEntity activationEntity = activationService.activateService(request.getIccid(),request.getCustomerEmail());
        logger.info("------------------------------------------------------");
        logger.info("Activation");
        logger.info("------------------------------------------------------``");
        return activationEntity;
    }

    @GetMapping("/get")
    public ResponseEntity<ActivationEntity> getActivationEntity(@RequestParam Long id){
        ActivationEntity entity = activationRepository.findById(id).orElse(null);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

}
