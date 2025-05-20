package apn.gov.aeroport.domain.dto;


import apn.gov.aeroport.domain.model.referentiels.Aeroport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AeroportDTO {
    private Long id;
    private String designation_fr;
    private String designation_ar;
    private String designation_en;
    private boolean valid;

    public static AeroportDTO fromEntity(Aeroport entity) {
        if (entity == null) return null;
        return AeroportDTO.builder()
                .id(entity.getId())
                .designation_fr(entity.getDesignation_fr())
                .designation_ar(entity.getDesignation_ar())
                .designation_en(entity.getDesignation_en())
                .valid(entity.isValid())
                .build();
    }

    public static Aeroport toEntity(AeroportDTO dto) {
        if (dto == null) return null;
        Aeroport entity = new Aeroport();
        entity.setId(dto.getId());
        entity.setDesignation_fr(dto.getDesignation_fr());
        entity.setDesignation_ar(dto.getDesignation_ar());
        entity.setDesignation_en(dto.getDesignation_en());
        entity.setValid(dto.isValid());
        return entity;
    }
}
