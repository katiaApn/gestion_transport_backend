package apn.gov.aeroport.repositories.referentiels;

import apn.gov.aeroport.domain.model.referentiels.Protocole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProtocoleRepository extends JpaRepository<Protocole, Long> {
}