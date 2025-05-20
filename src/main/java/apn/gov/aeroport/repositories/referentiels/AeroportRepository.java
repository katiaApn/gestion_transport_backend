package apn.gov.aeroport.repositories.referentiels;

import apn.gov.aeroport.domain.model.referentiels.Aeroport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AeroportRepository extends JpaRepository<Aeroport, Long> {

    List<Aeroport> findAllByValidTrue();

}