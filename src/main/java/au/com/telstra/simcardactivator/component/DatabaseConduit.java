package au.com.telstra.simcardactivator.component;

import au.com.telstra.simcardactivator.foundation.ActuationResult;
import au.com.telstra.simcardactivator.foundation.SimCard;
import au.com.telstra.simcardactivator.record.SimCardRecord;
import au.com.telstra.simcardactivator.repository.SimCardRecordRepository;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConduit {
    private final SimCardRecordRepository simCardRecordRepository;

    public DatabaseConduit(SimCardRecordRepository simCardRecordRepository) {
        this.simCardRecordRepository = simCardRecordRepository;
    }

    public void save(SimCard simCard, ActuationResult actuationResult) {
        SimCardRecord simCardRecord = new SimCardRecord(simCard, actuationResult);
        simCardRecordRepository.save(simCardRecord);
    }

    public SimCard querySimCard(long simCardId) {
        var simCardRecord = simCardRecordRepository.findById(simCardId).orElse(null);
        if (simCardRecord == null) {
            return null;
        }
        return new SimCard(simCardRecord);
    }
}
