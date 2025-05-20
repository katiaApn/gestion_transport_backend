package apn.gov.aeroport.services;

import apn.gov.aeroport.domain.dto.UserDTO;
import apn.gov.aeroport.domain.enums.TypeUser;
import apn.gov.aeroport.domain.model.users.Role;
import apn.gov.aeroport.domain.model.users.User;
import apn.gov.aeroport.exception.Exception;
import apn.gov.aeroport.exception.UserNotFoundException;
import apn.gov.aeroport.exception.UsernameAlreadyExistsException;
import apn.gov.aeroport.repositories.users.RoleRepository;
import apn.gov.aeroport.repositories.users.UserRepository;
import apn.gov.aeroport.security.config.CustomPasswordEncoder;
import apn.gov.aeroport.security.service.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder; // Injecter CustomPasswordEncoder

    @Autowired
    private AuthenticationManager authenticationManager; // Injecter AuthenticationManager

    public UserDTO save(UserDTO dto) {
        List<Role> roles = roleRepository.findAllById(dto.getRoleIds());

        // Vérifie si un utilisateur avec le même username existe
        User existingUser = userRepository.findByUsername(dto.getUsername()).orElse(null);

        if (existingUser != null && dto.getId() == null) {
            log.error("Le nom d'utilisateur '{}' est déjà utilisé.", dto.getUsername());
            throw new UsernameAlreadyExistsException("username_already_exists");
            //return null;
        }

        // Si création : encoder mot de passe + générer ID
        if (dto.getId() == null) {
            dto.setPassword(customPasswordEncoder.encode(dto.getPassword()));
            dto.setId(generateCustomId(dto.getType_user()));
        } else { // ajouter le cas de modification du mot de passe
            // Cas modification : récupérer l'utilisateur existant
            Optional<User> userOptional = userRepository.findById(dto.getId());
            if (userOptional.isPresent()) {
                User existing = userOptional.get();

                // Si le mot de passe a changé, on le crypte
                if (!customPasswordEncoder.matches(dto.getPassword(), existing.getPassword())) {
                    dto.setPassword(customPasswordEncoder.encode(dto.getPassword()));
                } else {
                    // Sinon, on garde l'ancien mot de passe déjà crypté
                    dto.setPassword(existing.getPassword());
                }
            } else {
                throw new RuntimeException("Utilisateur introuvable avec l'ID : " + dto.getId());
            }
        }

        // Création d’un nouveau compte
        User newUser = UserDTO.toEntity(dto, roles);


        log.info("Création d'un nouvel utilisateur : {}", dto.getUsername());
        return UserDTO.fromEntity(userRepository.save(newUser));
    }


    public UserDTO getUserById(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            return optionalUser.map(UserDTO::fromEntity).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public List<UserDTO> getAllUsers() {
//        List<UserDTO> users = new ArrayList<>();
//        try {
//            List<User> userList = userRepository.findAll();
//            for (User user : userList) {
//                users.add(UserDTO.fromEntity(user));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return users;
//    }

    // Modifiez la méthode getAllUsers pour ne retourner que les utilisateurs non supprimés
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<>();
        try {
            List<User> userList = userRepository.findBySupprimFalse(); // Nouvelle méthode à créer dans le repository
            for (User user : userList) {
                users.add(UserDTO.fromEntity(user));
            }
        } catch (Exception e) {
            log.error("Error fetching users", e);
        }
        return users;
    }

    public UserDTO updateUser(Long id, UserDTO userDto) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<Role> roles = roleRepository.findAllById(userDto.getRoleIds());
                user.setUsername(userDto.getUsername());
                user.setPassword(userDto.getPassword());
                user.setRoles(roles);
                user = userRepository.save(user);
                return UserDTO.fromEntity(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User login(LoginRequest loginRequest) {


//        User user = userRepository.findByUsername(loginRequest.getUsername())
//                .orElseThrow(() -> new UserNotFoundException("Nom Utilisateur non trouvé : " + loginRequest.getUsername()));


        User user = userRepository.findByUsernameAndSupprimFalse(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Nom Utilisateur non trouvé : " + loginRequest.getUsername()));

        try {
            // Authentifier l'utilisateur avec Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);


            return user;

        } catch (java.lang.Exception e) {
            throw new Exception("Invalid login credentials", "INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED);

        }

    }

    public Long generateCustomId(TypeUser typeUser) {
        long prefix;
        switch (typeUser) {
            case ADMIN -> prefix = 1;
            case PROTOCOLE -> prefix = 2;
            case DEPUTE -> prefix = 3;
            case GUEST -> prefix = 4;
            default -> prefix = 9;
        }

        long base = prefix * 1000L; // ex: 2000 pour protocole
        Long maxId = userRepository.findMaxIdInRange(base, base + 999);

        if (maxId != null && maxId >= base + 999) {
            throw new IllegalStateException("Limite atteinte pour le type d'utilisateur : " + typeUser);
        }

        return (maxId != null) ? maxId + 1 : base + 1;
    }


    // suppression logique des utilisateur a supprim = true
    public UserDTO logicalDeleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        User user = optionalUser.get();
        user.setSupprim(true); // Marquer comme supprimé logiquement
        User savedUser = userRepository.save(user);

        log.info("User logically deleted - ID: {}", id);
        return UserDTO.fromEntity(savedUser);
    }

    public UserDTO restoreUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found with id: " + id);
        }

        User user = optionalUser.get();
        user.setSupprim(false); // Restaurer l'utilisateur
        User savedUser = userRepository.save(user);

        log.info("User restored - ID: {}", id);
        return UserDTO.fromEntity(savedUser);
    }

    public List<UserDTO> getDeletedUsers() {
        return userRepository.findBySupprimTrue()
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Map<String, String>> resetPassword(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String newPassword = generatePasswordFromName(user.getNom(), user.getPrenom());
        user.setPassword(customPasswordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("newPassword", newPassword));
    }

    private String generatePasswordFromName(String nom, String prenom) {
        // 1ère et dernière lettre du Prénom en minuscule
        String prenomPart = "";
        if (prenom != null && !prenom.isEmpty()) {
            prenomPart = (prenom.charAt(0) + "").toLowerCase();
            if (prenom.length() > 1) {
                prenomPart += (prenom.charAt(prenom.length() - 1) + "").toLowerCase();
            }
        }

        // Nom complet en minuscule sans espaces
        String nomPart = "";
        if (nom != null && !nom.isEmpty()) {
            nomPart = nom.toLowerCase().replaceAll("\\s+", "");
        }

        // Combinaison + 2 chiffres aléatoires
        Random random = new Random();
        int randomNumber = random.nextInt(90) + 10; // Nombre entre 10 et 99

        return prenomPart + nomPart + randomNumber;
    }


}

