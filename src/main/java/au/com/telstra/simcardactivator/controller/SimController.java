package au.com.telstra.simcardactivator.controller;


import au.com.telstra.simcardactivator.models.ActuationResult;
import au.com.telstra.simcardactivator.models.SimCard;
import au.com.telstra.simcardactivator.service.SimCardActivation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimController {

    private final SimCardActivation simCardActivation;

    public SimController(SimCardActivation simCardActivation) {
        this.simCardActivation = simCardActivation;
    }

    @PostMapping("/sim-activate")
    public void activateSimRequest(@RequestBody SimCard simCard) {

        ActuationResult actuationResult= simCardActivation.actuate(simCard);
        System.out.println(actuationResult.getSuccess());
        System.out.println(simCard.toString());
    }
}
