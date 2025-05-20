package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.AeroportDTO;
import apn.gov.aeroport.domain.model.referentiels.Aeroport;
import apn.gov.aeroport.repositories.referentiels.AeroportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AeroportService {

    private final AeroportRepository aeroportRepository;

    public AeroportDTO save(AeroportDTO dto) {
        Aeroport aeroport = AeroportDTO.toEntity(dto);
        return AeroportDTO.fromEntity(aeroportRepository.save(aeroport));
    }

    public List<AeroportDTO> findAll() {
        return aeroportRepository.findAll()
                .stream()
                .map(AeroportDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public AeroportDTO findById(Long id) {
        return aeroportRepository.findById(id)
                .map(AeroportDTO::fromEntity)
                .orElse(null);
    }

    public void deleteById(Long id) {
        aeroportRepository.deleteById(id);
    }

    public List<AeroportDTO> findAllValid() {
        return aeroportRepository.findAllByValidTrue()
                .stream()
                .map(AeroportDTO::fromEntity)
                .toList();
    }

}
