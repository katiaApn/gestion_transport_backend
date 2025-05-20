package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.enums.TypeUser;
import apn.gov.aeroport.domain.model.referentiels.Protocole;
import apn.gov.aeroport.domain.model.users.Role;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtocoleDTO {

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

    private String fonction;
    private String departement;

    public static ProtocoleDTO fromEntity(Protocole entity) {
        if (entity == null) return null;

        return ProtocoleDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .dateNaissance(entity.getDateNaissance())
                .email(entity.getEmail())
                .telephone(entity.getTelephone())
                .type_user(entity.getType_user())
                .roleIds(entity.getRoles() != null ? entity.getRoles().stream().map(Role::getId).toList() : List.of())

                .fonction(entity.getFonction())
                .departement(entity.getDepartement())
                .build();
    }
}
