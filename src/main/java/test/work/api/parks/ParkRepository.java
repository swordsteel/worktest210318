package test.work.api.parks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkRepository extends JpaRepository<Park, UUID> {

    Optional<Park> findByParkCode(String parkCode);

}
