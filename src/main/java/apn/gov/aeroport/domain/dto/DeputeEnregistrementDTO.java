package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.model.referentiels.Depute;
import apn.gov.aeroport.domain.model.users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeputeEnregistrementDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;


    public static DeputeEnregistrementDTO fromEntity(Depute entity) {
        if (entity == null) return null;

        return DeputeEnregistrementDTO.builder()
                .id(entity.getId())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .telephone(entity.getTelephone())
                .build();
    }
}
