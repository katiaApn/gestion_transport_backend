package apn.gov.aeroport.repositories.enregistrements;

import apn.gov.aeroport.domain.dto.EnregistrementAeroportFullDTO;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementAeroport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnregistrementAeroportRepository extends JpaRepository<EnregistrementAeroport, Long> {

    @Query(value = """
    SELECT *
    FROM enregistrement_aeroport ea
    JOIN enregistrement_transport et ON ea.id = et.id
    WHERE et.date_arrivee BETWEEN (NOW() - INTERVAL '12 HOURS') AND (NOW() + INTERVAL '12 HOURS')
    """, nativeQuery = true)
    List<EnregistrementAeroport> findAllVolsToDayArriver();

    //WHERE DATE(et.date_depart) = CURRENT_DATE
    @Query(value = """
    SELECT *
    FROM enregistrement_aeroport ea
    JOIN enregistrement_transport et ON ea.id = et.id
    WHERE et.date_depart BETWEEN (NOW() - INTERVAL '12 HOURS') AND (NOW() + INTERVAL '12 HOURS')
    """, nativeQuery = true)
    List<EnregistrementAeroport> findAllVolsToDayDepart();
}