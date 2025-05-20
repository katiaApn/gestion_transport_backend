package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.WilayaDTO;
import apn.gov.aeroport.domain.model.referentiels.Wilaya;
import apn.gov.aeroport.repositories.referentiels.WilayaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WilayaService {

    private final WilayaRepository repository;

    public WilayaDTO save(WilayaDTO dto) {
        Wilaya entity = WilayaDTO.toEntity(dto);
        return WilayaDTO.fromEntity(repository.save(entity));
    }

    public List<WilayaDTO> findAll() {
        return repository.findAll().stream()
                .map(WilayaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<WilayaDTO> findById(Long id) {
        return repository.findById(id)
                .map(WilayaDTO::fromEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
