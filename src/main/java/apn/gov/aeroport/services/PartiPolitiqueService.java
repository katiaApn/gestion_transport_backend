package apn.gov.aeroport.services;
import apn.gov.aeroport.domain.dto.PartiPolitiqueDTO;
import apn.gov.aeroport.domain.model.referentiels.PartiPolitique;
import apn.gov.aeroport.repositories.referentiels.PartiPolitiqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartiPolitiqueService {

    private final PartiPolitiqueRepository repository;

    public PartiPolitiqueDTO save(PartiPolitiqueDTO dto) {
        PartiPolitique entity = PartiPolitiqueDTO.toEntity(dto);
        return PartiPolitiqueDTO.fromEntity(repository.save(entity));
    }

    public List<PartiPolitiqueDTO> findAll() {
        return repository.findAll().stream()
                .map(PartiPolitiqueDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<PartiPolitiqueDTO> findById(Long id) {
        return repository.findById(id)
                .map(PartiPolitiqueDTO::fromEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
