package au.com.telstra.simcardactivator.component;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/actuate")
public class ActuateController {
    @PostMapping
    public ResponseEntity<Map<String, Boolean>> handleActuateRequest(@RequestBody Map<String, String> iccidData) {
        String iccid = iccidData.get("iccid");

        boolean success = iccid != null && !iccid.isEmpty();
        HttpStatus status = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        System.out.println("hope the code got in here");
        return new ResponseEntity<>(Map.of("success", success), status);


    }
}
