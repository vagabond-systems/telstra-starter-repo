package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.ActuationResult;
import au.com.telstra.simcardactivator.foundation.SimCard;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimCardActivationRestController {
    private final SimCardActuationHandler simCardActuationHandler;

    public SimCardActivationRestController(SimCardActuationHandler simCardActuationHandler) {
        this.simCardActuationHandler = simCardActuationHandler;
    }


//    @PostMapping(value = "/activate")
//    public void handleActivationRequest(@RequestBody SimCard simCard){
//        var actuationResult = simCardActuationHandler.actuate(simCard);
//        System.out.println(actuationResult.getSuccess());
//        System.out.println("Approved");
//    }
    @PostMapping("/activate")
    public ActuationResult handleActivationRequest(@RequestBody SimCard simCard){
        var actuationResult = simCardActuationHandler.actuate(simCard);
        return actuationResult;
    }
}
