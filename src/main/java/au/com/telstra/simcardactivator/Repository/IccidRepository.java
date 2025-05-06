package au.com.telstra.simcardactivator.Repository;

import au.com.telstra.simcardactivator.Model.Iccid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IccidRepository extends JpaRepository<Iccid, Long> {
    Optional<Iccid> findByIccid(String iccid);
}
