package apn.gov.aeroport.domain.dto;


import apn.gov.aeroport.domain.model.referentiels.Wilaya;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WilayaDTO {
    private Long id;
    private String designation_fr;
    private String designation_ar;
    private String designation_en;
    private boolean valid;

    public static WilayaDTO fromEntity(Wilaya entity) {
        if (entity == null) return null;
        return WilayaDTO.builder()
                .id(entity.getId())
                .designation_fr(entity.getDesignation_fr())
                .designation_ar(entity.getDesignation_ar())
                .designation_en(entity.getDesignation_en())
                .valid(entity.isValid())
                .build();
    }

    public static Wilaya toEntity(WilayaDTO dto) {
        if (dto == null) return null;
        Wilaya entity = new Wilaya();
        entity.setId(dto.getId());
        entity.setDesignation_fr(dto.getDesignation_fr());
        entity.setDesignation_ar(dto.getDesignation_ar());
        entity.setDesignation_en(dto.getDesignation_en());
        entity.setValid(dto.isValid());
        return entity;
    }
}
