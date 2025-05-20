package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.enums.MoyenTransport;
import apn.gov.aeroport.domain.enums.TypeMotif;
import apn.gov.aeroport.domain.enums.TypeVoyage;
import apn.gov.aeroport.domain.enums.TypeTicket;
import apn.gov.aeroport.domain.enums.TypeVol;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementAeroport;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnregistrementAeroportFullDTO {

    // Champs hérités de EnregistrementTransport
    private Long id;
    private MoyenTransport moyen_transport;
    private LocalDateTime date_arrivee;
    private LocalDateTime date_depart;
    private Integer nbr_passagers;
    private TypeMotif type_motif;
    private String motif_voyage;
    private boolean supprim ;
    // Députés simplifiés
    private List<DeputeEnregistrementDTO> deputes;

    // Champs spécifiques de EnregistrementAeroport
    private String numero_vol_arrivee;
    private String numero_vol_depart;

    // IDs seuls
    private Long compagnie_depart_id;
    private Long compagnie_arrivee_id;
    private Long aeroport_depart_id;
    private Long aeroport_arrivee_id;

    // Objets complets pour accès aux libellés multilingues
    private CompanieDTO compagnie_depart;
    private CompanieDTO compagnie_arrivee;
    private AeroportDTO aeroport_depart;
    private AeroportDTO aeroport_arrivee;

    private TypeVol type_vol_depart;
    private TypeVol type_vol_arrivee;
    private TypeTicket type_ticket;
    private TypeVoyage type_voyage;

    public static EnregistrementAeroportFullDTO fromEntity(EnregistrementAeroport entity) {
        if (entity == null) return null;

        return EnregistrementAeroportFullDTO.builder()
                // Champs hérités
                .id(entity.getId())
                .moyen_transport(entity.getMoyen_transport())
                .date_arrivee(entity.getDate_arrivee())
                .date_depart(entity.getDate_depart())
                .nbr_passagers(entity.getNbr_passagers())
                .type_motif(entity.getType_motif())
                .motif_voyage(entity.getMotif_voyage())
                .supprim(entity.isSupprim())
                // Députés (objet DTO)
                .deputes(entity.getDeputes() != null
                        ? entity.getDeputes().stream()
                        .map(DeputeEnregistrementDTO::fromEntity)
                        .collect(Collectors.toList())
                        : null)

                // Vols
                .numero_vol_arrivee(entity.getNumero_vol_arrivee())
                .numero_vol_depart(entity.getNumero_vol_depart())

                // IDs seuls
                .compagnie_depart_id(entity.getCompagnie_depart() != null ? entity.getCompagnie_depart().getId() : null)
                .compagnie_arrivee_id(entity.getCompagnie_arrivee() != null ? entity.getCompagnie_arrivee().getId() : null)
                .aeroport_depart_id(entity.getAeroport_depart() != null ? entity.getAeroport_depart().getId() : null)
                .aeroport_arrivee_id(entity.getAeroport_arrivee() != null ? entity.getAeroport_arrivee().getId() : null)

                // Objets complets (pour désignations multilingues)
                .compagnie_depart(CompanieDTO.fromEntity(entity.getCompagnie_depart()))
                .compagnie_arrivee(CompanieDTO.fromEntity(entity.getCompagnie_arrivee()))
                .aeroport_depart(AeroportDTO.fromEntity(entity.getAeroport_depart()))
                .aeroport_arrivee(AeroportDTO.fromEntity(entity.getAeroport_arrivee()))

                // Enums restants
                .type_vol_depart(entity.getType_vol_depart())
                .type_vol_arrivee(entity.getType_vol_arrivee())
                .type_ticket(entity.getType_ticket())
                .type_voyage(entity.getType_voyage())
                .build();
    }
}
