package au.com.telstra.simcardactivator.Repositories;

import au.com.telstra.simcardactivator.models.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard,Long> {
}
