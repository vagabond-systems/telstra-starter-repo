package au.com.telstra.simcardactivator;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimActivationRecordRepository extends JpaRepository<SimActivationRecord, Long> {
}
