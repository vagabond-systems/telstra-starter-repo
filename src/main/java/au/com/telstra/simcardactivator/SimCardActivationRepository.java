package au.com.telstra.simcardactivator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SimCardActivationRepository extends JpaRepository<SimCardActivationRecord, Long> {
    
    /**
     * Find activation record by ICCID
     */
    Optional<SimCardActivationRecord> findByIccid(String iccid);
    
    /**
     * Find all activation records for a customer email
     */
    List<SimCardActivationRecord> findByCustomerEmail(String customerEmail);
    
    /**
     * Find all active SIM cards
     */
    List<SimCardActivationRecord> findByActiveTrue();
    
    /**
     * Find all inactive SIM cards
     */
    List<SimCardActivationRecord> findByActiveFalse();
    
    /**
     * Check if ICCID has been activated before
     */
    boolean existsByIccid(String iccid);
}
