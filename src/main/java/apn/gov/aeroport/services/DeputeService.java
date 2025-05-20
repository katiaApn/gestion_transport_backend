package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.DeputeDTO;
import apn.gov.aeroport.domain.dto.DeputeEnregistrementDTO;
import apn.gov.aeroport.domain.enums.TypeUser;
import apn.gov.aeroport.domain.model.referentiels.*;
import apn.gov.aeroport.repositories.referentiels.*;
import apn.gov.aeroport.repositories.users.RoleRepository;
import apn.gov.aeroport.security.config.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeputeService {

    private final DeputeRepository deputeRepository;
    private final TypeDeputeRepository typeDeputeRepository;
    private final PartiPolitiqueRepository partiPolitiqueRepository;
    private final PeriodeLegislatifRepository periodeLegislatifRepository;
    private final WilayaRepository wilayaRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private UserService userService; // pour accéder à generateCustomId
    @Autowired
    private CustomPasswordEncoder customPasswordEncoder; // Injecter CustomPasswordEncoder

    public DeputeDTO save(DeputeDTO dto) {
        try {
            //Depute entity = new Depute();
            Depute entity ;

            if (dto.getId() != null) {
                // Mise à jour → récupérer l'entité existante
                entity = deputeRepository.findById(dto.getId()).orElse(new Depute());
            } else {
                // Nouvelle création
                entity = new Depute();
            }
            //entity.setId(dto.getId());
            entity.setUsername(dto.getUsername());
            //entity.setPassword(customPasswordEncoder.encode(dto.getPassword())); // ← Important !

            if (dto.getId() == null ) {//|| !dto.getPassword().startsWith("$2")
                entity.setPassword(customPasswordEncoder.encode(dto.getPassword()));
                entity.setId(userService.generateCustomId(TypeUser.DEPUTE)); // pour généré le id
            } else {
                entity.setPassword(dto.getPassword());
                entity.setId(dto.getId());
            }

            entity.setType_user(TypeUser.DEPUTE); // ← Aussi important !
            entity.setNom(dto.getNom());
            entity.setPrenom(dto.getPrenom());
            entity.setDateNaissance(dto.getDateNaissance());
            entity.setEmail(dto.getEmail());
            entity.setTelephone(dto.getTelephone());


            if (dto.getTypeDeputeId() != null)
                entity.setType_depute(typeDeputeRepository.findById(dto.getTypeDeputeId()).orElse(null));

            if (dto.getPartiPolitiqueId() != null)
                entity.setParti_politique(partiPolitiqueRepository.findById(dto.getPartiPolitiqueId()).orElse(null));

            if (dto.getPeriodeLegislatifIds() != null)
                entity.setPeriode_legislatif(periodeLegislatifRepository.findAllById(dto.getPeriodeLegislatifIds()));

            if (dto.getWilayaId() != null)
                entity.setWilaya(wilayaRepository.findById(dto.getWilayaId()).orElse(null));

            if (dto.getRoleIds() != null)
                entity.setRoles(roleRepository.findAllById(dto.getRoleIds()));

            return DeputeDTO.fromEntity(deputeRepository.save(entity));
        } catch (Exception e) {
            log.error("Erreur lors de la sauvegarde du député", e);
            throw e;
        }
    }

    public List<DeputeDTO> findAll() {
        return deputeRepository.findAll().stream()
                .map(DeputeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<DeputeEnregistrementDTO> findAllDeputeEnregistrement() {
        return deputeRepository.findAll().stream()
                .map(DeputeEnregistrementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<DeputeDTO> findById(Long id) {
        return deputeRepository.findById(id)
                .map(DeputeDTO::fromEntity);
    }

    public void delete(Long id) {
        if (deputeRepository.existsById(id)) {
            deputeRepository.deleteById(id);
            log.info("Député supprimé: id={}", id);
        } else {
            log.warn("Député non trouvé pour suppression: id={}", id);
        }
    }
}
