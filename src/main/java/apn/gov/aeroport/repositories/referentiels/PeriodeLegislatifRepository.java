package apn.gov.aeroport.repositories.referentiels;

import apn.gov.aeroport.domain.model.referentiels.PeriodeLegislatif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeriodeLegislatifRepository extends JpaRepository<PeriodeLegislatif, Long> {

    PeriodeLegislatif findByValid(boolean b);
}