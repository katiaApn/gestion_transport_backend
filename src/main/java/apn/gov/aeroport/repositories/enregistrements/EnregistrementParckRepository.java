package apn.gov.aeroport.repositories.enregistrements;

import apn.gov.aeroport.domain.model.enregistrements.EnregistrementParck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnregistrementParckRepository extends JpaRepository<EnregistrementParck, Long> {
}