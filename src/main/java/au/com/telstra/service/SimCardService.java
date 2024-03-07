package au.com.telstra.service;

import au.com.telstra.model.SIMCard;
import au.com.telstra.repository.SimRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimCardService {
    private SimRepository simRepository;

    public SIMCard addSimCard(SIMCard simCard){
        return simRepository.save(simCard);
    }

    public SIMCard getSimCardById(long simCardId){
        return simRepository.findById(simCardId).isPresent()
                ? simRepository.findById(simCardId).get()
                : null;
    }

}
