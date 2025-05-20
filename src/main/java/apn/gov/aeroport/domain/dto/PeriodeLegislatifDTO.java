package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.model.referentiels.PeriodeLegislatif;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodeLegislatifDTO {
    private Long id;
    private String libeller_fr;
    private String libeller_ar;
    private String libeller_en;
    private String numero;
    private boolean valid;

    public static PeriodeLegislatifDTO fromEntity(PeriodeLegislatif entity) {
        if (entity == null) return null;
        return PeriodeLegislatifDTO.builder()
                .id(entity.getId())
                .libeller_fr(entity.getLibeller_fr())
                .libeller_ar(entity.getLibeller_ar())
                .libeller_en(entity.getLibeller_en())
                .numero(entity.getNumero())
                .valid(entity.isValid())
                .build();
    }

    public static PeriodeLegislatif toEntity(PeriodeLegislatifDTO dto) {
        if (dto == null) return null;
        PeriodeLegislatif entity = new PeriodeLegislatif();
        entity.setId(dto.getId());
        entity.setLibeller_fr(dto.getLibeller_fr());
        entity.setLibeller_ar(dto.getLibeller_ar());
        entity.setLibeller_en(dto.getLibeller_en());
        entity.setNumero(dto.getNumero());
        entity.setValid(dto.isValid());
        return entity;
    }
}
