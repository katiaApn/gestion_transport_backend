package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.enums.TypeTicket;
import apn.gov.aeroport.domain.enums.TypeVol;
import apn.gov.aeroport.domain.enums.TypeVoyage;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementAeroport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnregistrementAeroportDTO {
    private Long id;
    private String numero_vol_arrivee;
    private String numero_vol_depart;
    private Long compagnie_depart_id;
    private Long compagnie_arrivee_id;
    private Long aeroport_depart_id;
    private Long aeroport_arrivee_id;
    private TypeVol type_vol_depart;
    private TypeVol type_vol_arrivee;
    private TypeTicket type_ticket;
    private TypeVoyage type_voyage;

    public static EnregistrementAeroportDTO fromEntity(EnregistrementAeroport entity) {
        if (entity == null) return null;
        return EnregistrementAeroportDTO.builder()
                .id(entity.getId())
                .numero_vol_arrivee(entity.getNumero_vol_arrivee())
                .numero_vol_depart(entity.getNumero_vol_depart())
                .compagnie_depart_id(entity.getCompagnie_depart() != null ? entity.getCompagnie_depart().getId() : null)
                .compagnie_arrivee_id(entity.getCompagnie_arrivee() != null ? entity.getCompagnie_arrivee().getId() : null)
                .aeroport_depart_id(entity.getAeroport_depart() != null ? entity.getAeroport_depart().getId() : null)
                .aeroport_arrivee_id(entity.getAeroport_arrivee() != null ? entity.getAeroport_arrivee().getId() : null)
                .type_vol_depart(entity.getType_vol_depart())
                .type_vol_arrivee(entity.getType_vol_arrivee())
                .type_ticket(entity.getType_ticket())
                .type_voyage(entity.getType_voyage())
                .build();
    }
}
