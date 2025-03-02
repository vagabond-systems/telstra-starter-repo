package au.com.telstra.simcardactivator.Controller;

import au.com.telstra.simcardactivator.Model.Iccid;
import au.com.telstra.simcardactivator.Repository.IccidRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping()
public class IccidController {
    @Autowired
    IccidRepository iccidRepository;


    @Autowired
    RestTemplate restTemplate;


    @PostMapping("activate")
    public ResponseEntity<String> postToOtherService(@RequestBody Iccid iccid) {
        final String url = "http://localhost:8444/actuate";

        final String iccid_new = iccid.getIccid();

        System.out.println(iccid_new+ " : iccid_new");

        if (iccid_new == null || iccid_new.isEmpty()){
            return ResponseEntity.badRequest().body("ICCID cannot be null or empty");
        }
//        System.out.println(iccid_new instanceof String);

        Map<String, String> iccidData = new HashMap<>();
        iccidData.put("iccid", iccid_new);
//        System.out.println(iccidData.getClass() + "  : get class");
//        System.out.println(iccidData.values());
//        System.out.println((iccidData));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(iccidData, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            boolean success = jsonNode.get("success").asBoolean();
            iccid.setActive(success);

//            System.out.println(response.getBody());
//            System.out.println(iccid.getId());
//            System.out.println(iccid.getIccid());
//            System.out.println(iccid.getCustomerEmail());
//            System.out.println(iccid.isActive());
            iccidRepository.save(iccid);
            return response;
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>("Could not connect to the external service", HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // new code for task 2
    @GetMapping("query/{id}")
    public ResponseEntity<Iccid> getIccid(@PathVariable Long id){
        Optional<Iccid> iccid = iccidRepository.findById(id);
        return iccid.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//        return iccidRepository.findById(id);
    }

    @GetMapping("query")
    public List<Iccid> getAllIccid(){
        return iccidRepository.findAll();
    }




}
