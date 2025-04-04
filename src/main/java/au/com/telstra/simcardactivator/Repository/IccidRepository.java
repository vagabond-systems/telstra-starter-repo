package au.com.telstra.simcardactivator.Repository;

import au.com.telstra.simcardactivator.Model.Iccid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IccidRepository extends JpaRepository<Iccid, Long> {
}
