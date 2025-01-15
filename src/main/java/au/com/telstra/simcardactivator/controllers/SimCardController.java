package au.com.telstra.simcardactivator.controllers;

import au.com.telstra.simcardactivator.components.SimCardActuationHandler;
import au.com.telstra.simcardactivator.pojos.SimCardDTO;
import au.com.telstra.simcardactivator.entities.SimCard;
import au.com.telstra.simcardactivator.services.SimCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController

public class SimCardController {
    private final SimCardService simCardService;


    private final  SimCardActuationHandler simCardActuationHandler;
    private static final Logger logger = LoggerFactory.getLogger(SimCardController.class);
    @Autowired
    public SimCardController(SimCardService simCardService, SimCardActuationHandler simCardActuationHandler) {
        this.simCardService = simCardService;
        this.simCardActuationHandler = simCardActuationHandler;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSim(@RequestBody SimCardDTO simCardDTO) {
        var simCard = new SimCard(simCardDTO.getIccid(), simCardDTO.getCustomerEmail(), false);
        var actutaionResult = simCardActuationHandler.actuate(simCard);
        logger.info("Actuation success: {}", actutaionResult.getSuccess());
        if (actutaionResult.getSuccess()) {
            simCard.setActive(true);
            simCardService.saveSimCard(simCard);
            return ResponseEntity.ok("SIM activation successful.");
        } else {
            return ResponseEntity.status(500).body("SIM activation failed.");
        }
    }

    @GetMapping("/query")
    public SimCardDTO getSimCardQuery(@RequestParam long simCardId) {
        SimCard simCard = simCardService.getSimCardById(simCardId);
        if (simCard != null) {
            return new SimCardDTO(simCard.getIccid(), simCard.getCustomerEmail(), simCard.isActive());
        } else {
            return null;
        }
    }
}
