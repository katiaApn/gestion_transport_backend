package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.CompanieDTO;
import apn.gov.aeroport.domain.model.referentiels.Compagnie;
import apn.gov.aeroport.repositories.referentiels.CompagnieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompagnieService {

    private final CompagnieRepository compagnieRepository;

    public CompanieDTO save(CompanieDTO dto) {
        Compagnie compagnie = CompanieDTO.toEntity(dto);
        return CompanieDTO.fromEntity(compagnieRepository.save(compagnie));
    }

    public List<CompanieDTO> findAllValid() {
        return compagnieRepository.findAllByValidTrue()
                .stream()
                .map(CompanieDTO::fromEntity)
                .toList();
    }

    public List<CompanieDTO> findAll() {
        return compagnieRepository.findAll()
                .stream()
                .map(CompanieDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CompanieDTO findById(Long id) {
        return compagnieRepository.findById(id)
                .map(CompanieDTO::fromEntity)
                .orElse(null);
    }

    public void deleteById(Long id) {
        compagnieRepository.deleteById(id);
    }


}

