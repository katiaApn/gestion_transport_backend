package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.enums.MoyenTransport;
import apn.gov.aeroport.domain.enums.TypeMotif;
import apn.gov.aeroport.domain.enums.TypeVoyage;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementTransport;
import apn.gov.aeroport.domain.model.referentiels.Depute;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnregistrementTransportDTO {
    private Long id;
    private MoyenTransport moyen_transport;
    private LocalDateTime date_arrivee;
    private LocalDateTime date_depart;
    private Integer nbr_passagers;
    private TypeMotif type_motif;
    private String motif_voyage;
    private List<Long> deputesIds;
    private boolean supprim;
    private EnregistrementAeroportDTO enregistrementAeroportDTO;
    private EnregistrementParckDTO enregistrementParckDTO;

    public static EnregistrementTransportDTO fromEntity(EnregistrementTransport entity) {
        if (entity == null) return null;
        return EnregistrementTransportDTO.builder()
                .id(entity.getId())
                .moyen_transport(entity.getMoyen_transport())
                .date_arrivee(entity.getDate_arrivee())
                .date_depart(entity.getDate_depart())
                .nbr_passagers(entity.getNbr_passagers())
                .type_motif(entity.getType_motif())
                .motif_voyage(entity.getMotif_voyage())
                .supprim(entity.isSupprim())
                .deputesIds(entity.getDeputes().stream()
                        .map(Depute::getId)
                        .collect(Collectors.toList()))
                .build();
    }


}

