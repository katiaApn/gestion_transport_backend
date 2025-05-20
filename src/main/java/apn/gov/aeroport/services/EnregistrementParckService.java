package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.EnregistrementParckDTO;
import apn.gov.aeroport.domain.enums.MoyenTransport;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementParck;
import apn.gov.aeroport.domain.model.referentiels.Wilaya;
import apn.gov.aeroport.repositories.enregistrements.EnregistrementParckRepository;
import apn.gov.aeroport.repositories.referentiels.WilayaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnregistrementParckService {

    private final EnregistrementParckRepository repository;
    private final WilayaRepository wilayaRepository;
    private final EnregistrementTransportService enregistrementService;

    public EnregistrementParckDTO save(EnregistrementParckDTO dto) {
        EnregistrementParck entity = new EnregistrementParck();
        //entity.setId(dto.getId());

        if (dto.getId() == null) {
            dto.setId(enregistrementService.generateCustomId(MoyenTransport.VEHICULE)); // pour généré le id
        } else {
            entity.setId(dto.getId());
        }
        entity.setAdresse_destination(dto.getAdresse_destination());

        if (dto.getWilayaId() != null) {
            Wilaya wilaya = wilayaRepository.findById(dto.getWilayaId()).orElse(null);
            entity.setWilaya(wilaya);
        }

        return EnregistrementParckDTO.fromEntity(repository.save(entity));
    }

    public List<EnregistrementParckDTO> findAll() {
        return repository.findAll().stream()
                .map(EnregistrementParckDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<EnregistrementParckDTO> findById(Long id) {
        return repository.findById(id)
                .map(EnregistrementParckDTO::fromEntity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
