package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.EnregistrementAeroportDTO;
import apn.gov.aeroport.domain.dto.EnregistrementAeroportFullDTO;
import apn.gov.aeroport.domain.dto.EnregistrementAeroportGroupesDto;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementAeroport;
import apn.gov.aeroport.domain.model.referentiels.Aeroport;
import apn.gov.aeroport.domain.model.referentiels.Compagnie;
import apn.gov.aeroport.repositories.enregistrements.EnregistrementAeroportRepository;
import apn.gov.aeroport.domain.enums.MoyenTransport;
import apn.gov.aeroport.repositories.referentiels.AeroportRepository;
import apn.gov.aeroport.repositories.referentiels.CompagnieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnregistrementAeroportService {

    private final EnregistrementAeroportRepository enregistrementAeroportRepository;
    private final CompagnieRepository compagnieRepository;
    private final AeroportRepository aeroportRepository;
    private final EnregistrementTransportService enregistrementService;

    public EnregistrementAeroportDTO save(EnregistrementAeroportDTO dto) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("DTO est null");
            }

            EnregistrementAeroport entity = new EnregistrementAeroport();

           if (dto.getId() == null) {
            dto.setId(enregistrementService.generateCustomId(MoyenTransport.AVION)); // pour généré le id
            } else {
               // entity.setId(dto.getId());
               entity = enregistrementAeroportRepository.findById(dto.getId()).orElse(new EnregistrementAeroport());
            }

            entity.setNumero_vol_arrivee(dto.getNumero_vol_arrivee());
            entity.setNumero_vol_depart(dto.getNumero_vol_depart());
            entity.setType_vol_depart(dto.getType_vol_depart());
            entity.setType_vol_arrivee(dto.getType_vol_arrivee());
            entity.setType_ticket(dto.getType_ticket());
            entity.setType_voyage(dto.getType_voyage());

            if (dto.getCompagnie_depart_id() != null) {
                Compagnie compagnieDepart = compagnieRepository.findById(dto.getCompagnie_depart_id())
                        .orElseThrow(() -> new IllegalArgumentException("Compagnie départ introuvable pour id=" + dto.getCompagnie_depart_id()));
                entity.setCompagnie_depart(compagnieDepart);
            }

            if (dto.getCompagnie_arrivee_id() != null) {
                Compagnie compagnieArrivee = compagnieRepository.findById(dto.getCompagnie_arrivee_id())
                        .orElseThrow(() -> new IllegalArgumentException("Compagnie arrivée introuvable pour id=" + dto.getCompagnie_arrivee_id()));
                entity.setCompagnie_arrivee(compagnieArrivee);
            }

            if (dto.getAeroport_depart_id() != null) {
                Aeroport aeroportDepart = aeroportRepository.findById(dto.getAeroport_depart_id())
                        .orElseThrow(() -> new IllegalArgumentException("Aéroport départ introuvable pour id=" + dto.getAeroport_depart_id()));
                entity.setAeroport_depart(aeroportDepart);
            }

            if (dto.getAeroport_arrivee_id() != null) {
                Aeroport aeroportArrivee = aeroportRepository.findById(dto.getAeroport_arrivee_id())
                        .orElseThrow(() -> new IllegalArgumentException("Aéroport arrivée introuvable pour id=" + dto.getAeroport_arrivee_id()));
                entity.setAeroport_arrivee(aeroportArrivee);
            }

            EnregistrementAeroport saved = enregistrementAeroportRepository.save(entity);
            return EnregistrementAeroportDTO.fromEntity(saved);

        } catch (Exception e) {
            log.error("Erreur lors de la sauvegarde de l'enregistrement aéroport", e);
            throw e;
        }
    }

    public List<EnregistrementAeroportDTO> findAll() {
        return enregistrementAeroportRepository.findAll()
                .stream()
                .map(EnregistrementAeroportDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //@Override
    public List<EnregistrementAeroportFullDTO> findAllVols() {
        return enregistrementAeroportRepository.findAll().stream()
                .map(EnregistrementAeroportFullDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public EnregistrementAeroportGroupesDto findAllVolsDepartArriverToDay() {
        EnregistrementAeroportGroupesDto enregistrementAeroportGroupesDto = new EnregistrementAeroportGroupesDto();

        List<EnregistrementAeroport> enregistrementAeroportsDepart =  enregistrementAeroportRepository.findAllVolsToDayDepart();
        List<EnregistrementAeroport> enregistrementAeroportsArriver =  enregistrementAeroportRepository.findAllVolsToDayArriver();

        enregistrementAeroportGroupesDto.setEnregistrementAeroportsDepart(enregistrementAeroportsDepart);
        enregistrementAeroportGroupesDto.setEnregistrementAeroportsArrival(enregistrementAeroportsArriver);

        return enregistrementAeroportGroupesDto;
    }


    public Optional<EnregistrementAeroportDTO> findById(Long id) {
        return enregistrementAeroportRepository.findById(id)
                .map(EnregistrementAeroportDTO::fromEntity);
    }

    public void delete(Long id) {
        if (!enregistrementAeroportRepository.existsById(id)) {
            throw new IllegalArgumentException("Enregistrement non trouvé pour suppression id=" + id);
        }
        enregistrementAeroportRepository.deleteById(id);
    }
}

