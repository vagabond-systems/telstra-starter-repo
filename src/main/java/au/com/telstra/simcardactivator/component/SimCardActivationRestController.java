package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.SimCard;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimCardActivationRestController {
    private final DatabaseConduit databaseConduit;

    private final SimCardActuationHandler simCardActuationHandler;

    public SimCardActivationRestController(DatabaseConduit databaseConduit, SimCardActuationHandler simCardActuationHandler) {
        this.databaseConduit = databaseConduit;
        this.simCardActuationHandler = simCardActuationHandler;
    }

    @PostMapping(value = "/activate")
    public void handleActivationRequest(@RequestBody SimCard simCard) {
        var actuationResult = simCardActuationHandler.actuate(simCard);
        databaseConduit.save(simCard, actuationResult);
    }

    @GetMapping(value = "/query")
    public SimCard handleActivationRequest(@RequestParam Long simCardId) {
        return databaseConduit.querySimCard(simCardId);
    }
}
