package au.com.telstra.simcardactivator.Controller;

import au.com.telstra.simcardactivator.Model.Iccid;
import au.com.telstra.simcardactivator.Repository.IccidRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    private IccidRepository iccidRepository;
//
//    @Autowired
//    private Iccid iccid;


    @PostMapping
    public ResponseEntity<Map<String, Boolean>> handleActuateRequest(@RequestBody Map<String, String> iccidData) {
        String iccidString = iccidData.get("iccid");

        boolean success = iccidString != null && !iccidString.isEmpty();
//        boolean success;
//        if (iccidString != null && !iccidString.isEmpty()){
//            success = true;
//        } else {
//            success = false;
//        }
        HttpStatus status = success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;


        // new code for task 2
//        if (success){
//            iccid.setIccid(iccidString);
//            iccidRepository.save(iccid);
//        }

        return new ResponseEntity<>(Map.of("success", success), status);


    }
}

