package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.*;
import apn.gov.aeroport.domain.enums.MoyenTransport;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementAeroport;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementParck;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementTransport;
import apn.gov.aeroport.domain.model.referentiels.*;
import apn.gov.aeroport.exception.EnregistrementNotFoundException;
import apn.gov.aeroport.repositories.enregistrements.EnregistrementAeroportRepository;
import apn.gov.aeroport.repositories.enregistrements.EnregistrementParckRepository;
import apn.gov.aeroport.repositories.enregistrements.EnregistrementTransportRepository;
import apn.gov.aeroport.repositories.referentiels.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnregistrementTransportService {

    private final EnregistrementAeroportRepository aeroportRepository;
    private final EnregistrementParckRepository parckRepository;
    private final DeputeRepository deputeRepository;
    private final WilayaRepository wilayaRepository;
    private final EnregistrementTransportRepository transportRepository;
    private final CompagnieRepository compagnieRepository;
    private final AeroportRepository aeroportRepositorySimple;

    public EnregistrementTransportDTO save(EnregistrementTransportDTO dto) {
        try {
            if (dto.getMoyen_transport() == MoyenTransport.AVION && dto.getEnregistrementAeroportDTO() != null) {
                EnregistrementAeroport entity = new EnregistrementAeroport();
                mapCommun(dto, entity);

                var aeroDto = dto.getEnregistrementAeroportDTO();
                entity.setNumero_vol_arrivee(aeroDto.getNumero_vol_arrivee());
                entity.setNumero_vol_depart(aeroDto.getNumero_vol_depart());
                entity.setType_vol_depart(aeroDto.getType_vol_depart());
                entity.setType_vol_arrivee(aeroDto.getType_vol_arrivee());
                entity.setType_ticket(aeroDto.getType_ticket());
                entity.setType_voyage(aeroDto.getType_voyage());

                // Charger les compagnies et aéroports par leurs IDs
                if (aeroDto.getCompagnie_depart_id() != null) {
                    entity.setCompagnie_depart(compagnieRepository.findById(aeroDto.getCompagnie_depart_id())
                            .orElseThrow(() -> new IllegalArgumentException("Compagnie de départ introuvable")));
                }
                if (aeroDto.getCompagnie_arrivee_id() != null) {
                    entity.setCompagnie_arrivee(compagnieRepository.findById(aeroDto.getCompagnie_arrivee_id())
                            .orElseThrow(() -> new IllegalArgumentException("Compagnie d'arrivée introuvable")));
                }
                if (aeroDto.getAeroport_depart_id() != null) {
                    entity.setAeroport_depart(aeroportRepositorySimple.findById(aeroDto.getAeroport_depart_id())
                            .orElseThrow(() -> new IllegalArgumentException("Aéroport de départ introuvable")));
                }
                if (aeroDto.getAeroport_arrivee_id() != null) {
                    entity.setAeroport_arrivee(aeroportRepositorySimple.findById(aeroDto.getAeroport_arrivee_id())
                            .orElseThrow(() -> new IllegalArgumentException("Aéroport d'arrivée introuvable")));
                }

                if (dto.getId() == null) {
                    entity.setId(generateCustomId(dto.getMoyen_transport()));
                } else {
                    entity.setId(dto.getId());
                }

                return EnregistrementTransportDTO.fromEntity(aeroportRepository.save(entity));

            } else if (dto.getMoyen_transport() == MoyenTransport.VEHICULE && dto.getEnregistrementParckDTO() != null) {
                EnregistrementParck entity = new EnregistrementParck();
                mapCommun(dto, entity);

                var parckDto = dto.getEnregistrementParckDTO();
                entity.setAdresse_destination(parckDto.getAdresse_destination());

                if (parckDto.getWilayaId() != null) {
                    entity.setWilaya(wilayaRepository.findById(parckDto.getWilayaId())
                            .orElseThrow(() -> new IllegalArgumentException("Wilaya introuvable")));
                }

                if (dto.getId() == null) {
                    entity.setId(generateCustomId(dto.getMoyen_transport()));
                } else {
                    entity.setId(dto.getId());
                }

                return EnregistrementTransportDTO.fromEntity(parckRepository.save(entity));

            } else {
                throw new IllegalArgumentException("Type de transport ou données manquantes");
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement d'un transport", e);
            throw e;
        }
    }

    public List<EnregistrementTransportDTO> findAll() {
        try {
            List<EnregistrementTransportDTO> all = aeroportRepository.findAll().stream()
                    .map(EnregistrementTransportDTO::fromEntity)
                    .collect(Collectors.toList());

            all.addAll(parckRepository.findAll().stream()
                    .map(EnregistrementTransportDTO::fromEntity)
                    .collect(Collectors.toList()));

            return all;
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des enregistrements", e);
            throw e;
        }
    }

    public Optional<EnregistrementTransportDTO> findById(Long id) {
        try {
            return aeroportRepository.findById(id)
                    .map(EnregistrementTransportDTO::fromEntity)
                    .or(() -> parckRepository.findById(id).map(EnregistrementTransportDTO::fromEntity));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'enregistrement id={}", id, e);
            throw e;
        }
    }

    public void delete(Long id) {
        try {
            if (aeroportRepository.existsById(id)) {
                aeroportRepository.deleteById(id);
            } else if (parckRepository.existsById(id)) {
                parckRepository.deleteById(id);
            } else {
                log.warn("Enregistrement introuvable pour suppression: id={}", id);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'enregistrement id={}", id, e);
            throw e;
        }
    }

    private void mapCommun(EnregistrementTransportDTO dto, EnregistrementTransport entity) {
        entity.setMoyen_transport(dto.getMoyen_transport());
        entity.setDate_arrivee(dto.getDate_arrivee());
        entity.setDate_depart(dto.getDate_depart());
        entity.setNbr_passagers(dto.getNbr_passagers());
        entity.setMotif_voyage(dto.getMotif_voyage());
        entity.setType_motif(dto.getType_motif());

        if (dto.getDeputesIds() != null) {
            List<Depute> deputes = deputeRepository.findAllById(dto.getDeputesIds());
            entity.setDeputes(deputes);
        }
    }

    public Long generateCustomId(MoyenTransport type) {
        long prefix = switch (type) {
            case AVION -> 1;
            case VEHICULE -> 2;
        };

        long base = prefix * 1000L;
        Long maxId = transportRepository.findMaxIdInRange(base, base + 9999);

        if (maxId != null && maxId >= base + 9999) {
            throw new IllegalStateException("Limite atteinte pour le type d’enregistrement : " + type);
        }

        return (maxId != null) ? maxId + 1 : base + 1;
    }


    public void logicalDelete(Long id) {
        try {
            if (aeroportRepository.existsById(id)) {
                aeroportRepository.deleteById(id);
            } else if (parckRepository.existsById(id)) {

                parckRepository.deleteById(id);
            } else {
                log.warn("Enregistrement introuvable pour suppression: id={}", id);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'enregistrement id={}", id, e);
            throw e;
        }
    }

    public EnregistrementTransportDTO logicalDeleteEnregistrement(Long id) {
        // Vérifier d'abord dans aeroportRepository
        Optional<EnregistrementAeroport> optionalVol = aeroportRepository.findById(id);
        if (optionalVol.isPresent()) {
            EnregistrementAeroport enregistrement = optionalVol.get();
            enregistrement.setSupprim(true);
            EnregistrementAeroport saved = aeroportRepository.save(enregistrement);
            log.info("************Enregistrement Aeroport logically deleted - ID: {}", id);
            return EnregistrementTransportDTO.fromEntity(saved);
        }

        // Si non trouvé, vérifier dans parckRepository
        Optional<EnregistrementParck> optionalParck = parckRepository.findById(id);
        if (optionalParck.isPresent()) {
            EnregistrementParck enregistrement = optionalParck.get();
            enregistrement.setSupprim(true);
            EnregistrementParck saved = parckRepository.save(enregistrement);
            log.info("Enregistrement Parck logically deleted - ID: {}", id);
            return EnregistrementTransportDTO.fromEntity(saved);
        }

        // Si aucun enregistrement trouvé
        throw new EnregistrementNotFoundException("Enregistrement not found with id: " + id);
    }

}
