package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.enums.TypeUser;
import apn.gov.aeroport.domain.model.referentiels.Depute;
import apn.gov.aeroport.domain.model.users.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeputeDTO {
    private Long id;
    private String username;
    private String password;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String telephone;
    private TypeUser type_user;

    private List<Long> roleIds;

    // IDs seuls
    private Long typeDeputeId;
    private Long partiPolitiqueId;
    private List<Long> periodeLegislatifIds;
    private Long wilayaId;

    // Objets complets pour accès aux libellés multilingues
    private TypeDeputeDTO typeDepute;
    private PartiPolitiqueDTO partiPolitique;
    private List<PeriodeLegislatifDTO> periodeLegislatif;
    private WilayaDTO wilaya;

    public static DeputeDTO fromEntity(Depute entity) {
        if (entity == null) return null;

        return DeputeDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .dateNaissance(entity.getDateNaissance())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .type_user(entity.getType_user())
                .roleIds(entity.getRoles() != null ? entity.getRoles().stream().map(Role::getId).toList() : List.of())
                // IDs seuls
                .typeDeputeId(entity.getType_depute() != null ? entity.getType_depute().getId() : null)
                .partiPolitiqueId(entity.getParti_politique() != null ? entity.getParti_politique().getId() : null)
                .periodeLegislatifIds(entity.getPeriode_legislatif().stream().map(p -> p.getId()).toList())
                .wilayaId(entity.getWilaya() != null ? entity.getWilaya().getId() : null)

                // Objets complets (pour désignations multilingues)
                .typeDepute(TypeDeputeDTO.fromEntity(entity.getType_depute()))
                .partiPolitique(PartiPolitiqueDTO.fromEntity(entity.getParti_politique()))
                .periodeLegislatif(entity.getPeriode_legislatif() != null
                        ? entity.getPeriode_legislatif().stream()
                        .map(PeriodeLegislatifDTO::fromEntity)
                        .collect(Collectors.toList())
                        : null)
                //.periodeLegislatifIds(entity.getPeriode_legislatif().stream().map(p -> p.getId()).toList())
                .wilaya(WilayaDTO.fromEntity(entity.getWilaya()))


                .build();
    }
}
