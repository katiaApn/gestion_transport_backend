package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.model.referentiels.Compagnie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanieDTO {
    private Long id;
    private String designation_fr;
    private String designation_ar;
    private String designation_en;
    private boolean valid;

    public static CompanieDTO fromEntity(Compagnie entity) {
        if (entity == null) return null;
        return CompanieDTO.builder()
                .id(entity.getId())
                .designation_fr(entity.getDesignation_fr())
                .designation_ar(entity.getDesignation_ar())
                .designation_en(entity.getDesignation_en())
                .valid(entity.isValid())
                .build();
    }

    public static Compagnie toEntity(CompanieDTO dto) {
        if (dto == null) return null;
        Compagnie entity = new Compagnie();
        entity.setId(dto.getId());
        entity.setDesignation_fr(dto.getDesignation_fr());
        entity.setDesignation_ar(dto.getDesignation_ar());
        entity.setDesignation_en(dto.getDesignation_en());
        entity.setValid(dto.isValid());
        return entity;
    }
}
