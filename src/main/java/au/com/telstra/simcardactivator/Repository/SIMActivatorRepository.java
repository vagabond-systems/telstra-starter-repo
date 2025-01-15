package au.com.telstra.simcardactivator.Repository;

import au.com.telstra.simcardactivator.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SIMActivatorRepository extends JpaRepository<Customer,Long> {

    Customer getByIccid(String iccid);
}
