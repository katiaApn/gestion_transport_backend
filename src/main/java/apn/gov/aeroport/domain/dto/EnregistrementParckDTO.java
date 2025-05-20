package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.model.enregistrements.EnregistrementParck;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnregistrementParckDTO {
    private Long id;
//    private MoyenTransport moyen_transport;
//    private LocalDateTime date_arrivee;
//    private LocalDateTime date_depart;
//    private Integer nbr_passagers;
//    private TypeVoyage type_voyage;
//    private String motif_voyage;
//    private List<Long> deputesIds;
    private String adresse_destination;
    private Long wilayaId;

    public static EnregistrementParckDTO fromEntity(EnregistrementParck entity) {
        if (entity == null) return null;

        return EnregistrementParckDTO.builder()
                .id(entity.getId())
//                .moyen_transport(entity.getMoyen_transport())
//                .date_arrivee(entity.getDate_arrivee())
//                .date_depart(entity.getDate_depart())
//                .nbr_passagers(entity.getNbr_passagers())
//                .type_voyage(entity.getType_voyage())
//                .motif_voyage(entity.getMotif_voyage())
                .adresse_destination(entity.getAdresse_destination())
                .wilayaId(entity.getWilaya() != null ? entity.getWilaya().getId() : null)
//                .deputesIds(entity.getDeputes().stream().map(d -> d.getId().longValue()).collect(Collectors.toList()))
                .build();
    }
}
