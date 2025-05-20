package apn.gov.aeroport.services;


import apn.gov.aeroport.domain.dto.TypeDeputeDTO;
import apn.gov.aeroport.domain.model.referentiels.TypeDepute;
import apn.gov.aeroport.repositories.referentiels.TypeDeputeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeDeputeService {

    private final TypeDeputeRepository repository;

    public TypeDeputeDTO save(TypeDeputeDTO dto) {
        TypeDepute entity = TypeDeputeDTO.toEntity(dto);
        return TypeDeputeDTO.fromEntity(repository.save(entity));
    }

    public List<TypeDeputeDTO> findAll() {
        return repository.findAll().stream()
                .map(TypeDeputeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<TypeDeputeDTO> findById(Long id) {
        return repository.findById(id)
                .map(TypeDeputeDTO::fromEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
