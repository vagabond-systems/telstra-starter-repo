package au.com.telstra.simcardactivator.services;

import au.com.telstra.simcardactivator.entities.SimCard;
import au.com.telstra.simcardactivator.repositories.SimCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimCardService {
    private final SimCardRepository simCardRepository;

    @Autowired
    public SimCardService(SimCardRepository simCardRepository) {
        this.simCardRepository = simCardRepository;
    }
    public SimCard saveSimCard(SimCard simCard) {
        return simCardRepository.save(simCard);
    }


    public SimCard getSimCardById(Long id) {
        return simCardRepository.findById(id).orElse(null); // Return null if not found
    }


    public SimCard getSimCardByIccid(String iccid) {
        return simCardRepository.findByIccid(iccid);
    }

}
