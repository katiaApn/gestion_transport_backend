package apn.gov.aeroport.repositories.enregistrements;

import apn.gov.aeroport.domain.model.enregistrements.EnregistrementTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnregistrementTransportRepository extends JpaRepository<EnregistrementTransport, Long> {
    //Long findMaxIdInRange(long base, long l);
    @Query("SELECT MAX(e.id) FROM EnregistrementTransport e WHERE e.id BETWEEN :start AND :end")
    Long findMaxIdInRange(@Param("start") Long start, @Param("end") Long end);
}