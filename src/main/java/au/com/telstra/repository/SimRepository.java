package au.com.telstra.repository;

import au.com.telstra.model.SIMCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimRepository extends JpaRepository<SIMCard,Long> {
    @Override
    <S extends SIMCard> S save(S entity);

    @Override
    Optional<SIMCard> findById(Long aLong);
}
