package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.model.referentiels.TypeDepute;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TypeDeputeDTO {
    private Long id;
    private String libeller_fr;
    private String libeller_ar;
    private String libeller_en;
    private boolean valid;

    public static TypeDeputeDTO fromEntity(TypeDepute entity) {
        if (entity == null) return null;
        return TypeDeputeDTO.builder()
                .id(entity.getId())
                .libeller_fr(entity.getLibeller_fr())
                .libeller_ar(entity.getLibeller_ar())
                .libeller_en(entity.getLibeller_en())
                .valid(entity.isValid())
                .build();
    }

    public static TypeDepute toEntity(TypeDeputeDTO dto) {
        if (dto == null) return null;
        TypeDepute entity = new TypeDepute();
        entity.setId(dto.getId());
        entity.setLibeller_fr(dto.getLibeller_fr());
        entity.setLibeller_ar(dto.getLibeller_ar());
        entity.setLibeller_en(dto.getLibeller_en());
        entity.setValid(dto.isValid());
        return entity;
    }
}








