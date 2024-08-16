package au.com.telstra.simcardactivator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.telstra.simcardactivator.entity.SimCardActivator;

public interface SimCardActivatorRepository extends JpaRepository<SimCardActivator, Long> {

}
