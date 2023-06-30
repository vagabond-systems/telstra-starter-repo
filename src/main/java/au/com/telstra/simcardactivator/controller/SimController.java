package au.com.telstra.simcardactivator.controller;


import au.com.telstra.simcardactivator.models.ActuationResult;
import au.com.telstra.simcardactivator.models.SimCard;
import au.com.telstra.simcardactivator.service.SimCardActivation;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SimController {

    private final SimCardActivation simCardService;

    public SimController(SimCardActivation simCardActivation) {
        this.simCardService = simCardActivation;
    }

    @PostMapping("/sim-activate")
    public void activateSimRequest(@RequestBody SimCard simCard) {

        ActuationResult actuationResult = simCardService.actuate(simCard);
        System.out.println(actuationResult.getSuccess());
        simCard.setActive(actuationResult.getSuccess());
        simCardService.saveSimCard(simCard);
        System.out.println(simCard.toString());
    }

    @GetMapping("/sim-cards/{id}")
    public Optional<SimCard> getSingleSim(@PathVariable Long id) {
        return simCardService.getById(id);
    }
}
