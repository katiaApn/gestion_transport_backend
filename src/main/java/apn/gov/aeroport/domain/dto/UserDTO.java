package apn.gov.aeroport.domain.dto;

import apn.gov.aeroport.domain.enums.TypeUser;
import apn.gov.aeroport.domain.model.users.Role;
import apn.gov.aeroport.domain.model.users.User;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    //private List<Role> roles;
    private List<Long> roleIds;

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String telephone;

    private TypeUser type_user;

    private String accessToken;
    private String refreshToken;

    private Instant creationDate;
    private Instant lastModifiedDate;

    private boolean supprim;

    public static UserDTO fromEntity(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        //dto.setPassword(user.getPassword());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setDateNaissance(user.getDateNaissance());
        dto.setEmail(user.getEmail());
        dto.setTelephone(user.getTelephone());
        dto.setType_user(user.getType_user());
        dto.setCreationDate(user.getCreationDate());
        dto.setLastModifiedDate(user.getLastModifiedDate());
        dto.setSupprim(user.isSupprim());
        //dto.setRoles(user.getRoles() != null ? new ArrayList<>(user.getRoles()) : new ArrayList<>());
        dto.setRoleIds(
                user.getRoles() != null
                        ? user.getRoles().stream().map(Role::getId).toList() //role -> role.getId().longValue()
                        : List.of()
        );
        return dto;
    }

    public static User toEntity(UserDTO dto, List<Role> roles) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setDateNaissance(dto.getDateNaissance());
        user.setEmail(dto.getEmail());
        user.setTelephone(dto.getTelephone());
        user.setType_user(dto.getType_user());
        //user.setRoles(dto.getRoles() != null ? new ArrayList<>(dto.getRoles()) : new ArrayList<>());
        user.setRoles(roles); // les rôles sont fournis depuis le service
        user.setSupprim(dto.isSupprim());
        return user;
    }
}
