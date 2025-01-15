package au.com.telstra.simcardactivator.repositories;

import au.com.telstra.simcardactivator.entities.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, Long> {
    SimCard findByIccid(String iccid);
}