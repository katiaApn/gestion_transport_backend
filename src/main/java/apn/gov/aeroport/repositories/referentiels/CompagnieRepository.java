package apn.gov.aeroport.repositories.referentiels;

import apn.gov.aeroport.domain.model.referentiels.Compagnie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompagnieRepository extends JpaRepository<Compagnie, Long> {

    List<Compagnie> findAllByValidTrue();

}