package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.ProtocoleDTO;
import apn.gov.aeroport.domain.enums.TypeUser;
import apn.gov.aeroport.domain.model.referentiels.Depute;
import apn.gov.aeroport.domain.model.referentiels.Protocole;
import apn.gov.aeroport.domain.model.users.Role;
import apn.gov.aeroport.repositories.referentiels.ProtocoleRepository;
import apn.gov.aeroport.repositories.users.RoleRepository;
import apn.gov.aeroport.security.config.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProtocoleService {

    private final ProtocoleRepository protocoleRepository;
    private final RoleRepository roleRepository;
    private final CustomPasswordEncoder customPasswordEncoder;
    @Autowired
    private UserService userService; // pour accéder à generateCustomId

    public ProtocoleDTO save(ProtocoleDTO dto) {
        try {

            Protocole entity ;

            if (dto.getId() != null) {
                // Mise à jour → récupérer l'entité existante
                entity = protocoleRepository.findById(dto.getId()).orElse(new Protocole());
            } else {
                // Nouvelle création
                entity = new Protocole();
            }
            //Protocole entity =new Protocole();
            //entity.setId(dto.getId());
            entity.setUsername(dto.getUsername());
            //entity.setPassword(customPasswordEncoder.encode(dto.getPassword()));
            // Hasher uniquement si ce n'est pas déjà hashé
            if (dto.getId() == null || !dto.getPassword().startsWith("$2")) {
                entity.setPassword(customPasswordEncoder.encode(dto.getPassword()));
                entity.setId(userService.generateCustomId(TypeUser.PROTOCOLE)); // pour généré le id
            } else {
                entity.setPassword(dto.getPassword());
                entity.setId(dto.getId());
            }

            entity.setType_user(TypeUser.PROTOCOLE);
            entity.setNom(dto.getNom());
            entity.setPrenom(dto.getPrenom());
            entity.setDateNaissance(dto.getDateNaissance());
            entity.setEmail(dto.getEmail());
            entity.setTelephone(dto.getTelephone());
            entity.setFonction(dto.getFonction());
            entity.setDepartement(dto.getDepartement());

            if (dto.getRoleIds() != null) {
                List<Role> roles = roleRepository.findAllById(dto.getRoleIds());
                entity.setRoles(roles);
            }

            return ProtocoleDTO.fromEntity(protocoleRepository.save(entity));
        } catch (Exception e) {
            log.error("Erreur lors de la sauvegarde du protocole", e);
            throw e;
        }
    }

    public List<ProtocoleDTO> findAll() {
        return protocoleRepository.findAll().stream()
                .map(ProtocoleDTO::fromEntity)
                .toList();
    }

    public Optional<ProtocoleDTO> findById(Long id) {
        return protocoleRepository.findById(id)
                .map(ProtocoleDTO::fromEntity);
    }

    public void delete(Long id) {
        if (protocoleRepository.existsById(id)) {
            protocoleRepository.deleteById(id);
            log.info("Protocole supprimé: id={}", id);
        } else {
            log.warn("Protocole non trouvé pour suppression: id={}", id);
        }
    }
}
