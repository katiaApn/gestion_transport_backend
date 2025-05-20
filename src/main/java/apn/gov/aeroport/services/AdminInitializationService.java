package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.enums.Roles;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementParck;
import apn.gov.aeroport.domain.model.enregistrements.EnregistrementTransport;
import apn.gov.aeroport.domain.model.users.Role;
import apn.gov.aeroport.repositories.users.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminInitializationService {
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initializeRoles() {
        if (!roleRepository.findByName(Roles.ROLE_ADMIN).isPresent()) {
            roleRepository.save(new Role(Roles.ROLE_ADMIN));
        }
        if (!roleRepository.findByName(Roles.ROLE_AEROPORT).isPresent()) {
            roleRepository.save(new Role(Roles.ROLE_AEROPORT));
        }

        if (!roleRepository.findByName(Roles.ROLE_PARCK).isPresent()) {
            roleRepository.save(new Role(Roles.ROLE_PARCK));
        }

        if (!roleRepository.findByName(Roles.ROLE_PROTOCOLE).isPresent()) {
            roleRepository.save(new Role(Roles.ROLE_PROTOCOLE));
        }

        if (!roleRepository.findByName(Roles.ROLE_DEPUTE).isPresent()) {
            roleRepository.save(new Role(Roles.ROLE_DEPUTE));
        }

    }

}
