package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.model.referentiels.PartiPolitique;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartiPolitiqueDTO {
    private Long id;
    private String designation_fr;
    private String designation_ar;
    private String designation_en;
    private boolean valid;

    public static PartiPolitiqueDTO fromEntity(PartiPolitique entity) {
        if (entity == null) return null;
        return PartiPolitiqueDTO.builder()
                .id(entity.getId())
                .designation_fr(entity.getDesignation_fr())
                .designation_ar(entity.getDesignation_ar())
                .designation_en(entity.getDesignation_en())
                .valid(entity.isValid())
                .build();
    }

    public static PartiPolitique toEntity(PartiPolitiqueDTO dto) {
        if (dto == null) return null;
        PartiPolitique entity = new PartiPolitique();
        entity.setId(dto.getId());
        entity.setDesignation_fr(dto.getDesignation_fr());
        entity.setDesignation_ar(dto.getDesignation_ar());
        entity.setDesignation_en(dto.getDesignation_en());
        entity.setValid(dto.isValid());
        return entity;
    }
}
