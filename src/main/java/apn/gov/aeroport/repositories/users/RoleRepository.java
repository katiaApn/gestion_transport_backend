package apn.gov.aeroport.repositories.users;

import apn.gov.aeroport.domain.enums.Roles;
import apn.gov.aeroport.domain.model.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles roleName);
}