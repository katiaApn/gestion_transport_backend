package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.PeriodeLegislatifDTO;
import apn.gov.aeroport.domain.model.referentiels.PeriodeLegislatif;
import apn.gov.aeroport.repositories.referentiels.PeriodeLegislatifRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeriodeLegislatifService {

    private final PeriodeLegislatifRepository repository;

    public PeriodeLegislatifDTO save(PeriodeLegislatifDTO dto) {
        PeriodeLegislatif entity = PeriodeLegislatifDTO.toEntity(dto);
        return PeriodeLegislatifDTO.fromEntity(repository.save(entity));
    }

    public List<PeriodeLegislatifDTO> findAll() {
        return repository.findAll().stream()//.findAll().stream() pour garder que la période valid
                .map(PeriodeLegislatifDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<PeriodeLegislatifDTO> findById(Long id) {
        return repository.findById(id)
                .map(PeriodeLegislatifDTO::fromEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PeriodeLegislatifDTO  getActivePeriodeLegislatif() {
        //return repository.findByValid(true);  // Retourne la période législative active

        PeriodeLegislatif entity =  repository.findByValid(true);
        if (entity == null) {
            return null;
        }
        return mapToDTO(entity);
    }


    private PeriodeLegislatifDTO mapToDTO(PeriodeLegislatif entity) {
        PeriodeLegislatifDTO dto = new PeriodeLegislatifDTO();
        dto.setId(entity.getId());
        dto.setLibeller_fr(entity.getLibeller_fr());
        dto.setLibeller_ar(entity.getLibeller_ar());
        dto.setLibeller_en(entity.getLibeller_en());
        dto.setValid(entity.isValid()); // <-- important
        return dto;
    }


}
