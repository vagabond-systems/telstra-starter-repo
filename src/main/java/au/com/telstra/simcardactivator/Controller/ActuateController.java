package au.com.telstra.simcardactivator.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/actuate")
public class ActuateController {


    @PostMapping
    public ResponseEntity<Map<String, Boolean>> handleActuateRequest(@RequestBody Map<String, String> iccidData) {
        String iccid = iccidData.get("iccid");

        boolean success = iccid != null && !iccid.isEmpty();
        HttpStatus status = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(Map.of("success", success), status);


    }
}

