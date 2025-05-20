package apn.gov.aeroport.controllers;


import apn.gov.aeroport.domain.dto.UserDTO;
import apn.gov.aeroport.domain.model.users.User;
import apn.gov.aeroport.exception.UsernameAlreadyExistsException;
import apn.gov.aeroport.security.service.JwtService;
import apn.gov.aeroport.security.service.LoginRequest;
import apn.gov.aeroport.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping(value ="/register")
    public UserDTO createUser(@RequestBody UserDTO userDto) {
        try {
            return userService.save(userDto);
            //UserDTO savedUser = userService.save(userDTO); // Appel au service qui peut lever l'exception
            //return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (UsernameAlreadyExistsException ex) {
            throw ex; // Lève l'exception pour que @ControllerAdvice la gère
        }
    }

    @GetMapping("/getUserById/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        try {
            return userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping ("/getAllUsers")
    public List<UserDTO> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
        try {
            return userService.updateUser(id, userDto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        try {
            return userService.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.login(loginRequest);

        UserDTO userDto = UserDTO.fromEntity(user);

        var token = jwtService.generateToken(user);
        HttpHeaders reponseHeaders = new HttpHeaders();
        reponseHeaders.add("Access-Control-Expose-Headers", "Authorization");
        reponseHeaders.add("Authorization", "Bearer " + token);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User login successfully");
        userDto.setAccessToken(token);
        userDto.setRefreshToken("");
        response.put("user", userDto);
        logger.info("Login successfully : {}", userDto.getUsername());

        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(reponseHeaders).body(response);
    }

    @PatchMapping("/{id}/logicalDeleteUser")
    public ResponseEntity<UserDTO> logicalDelete(@PathVariable Long id) {
        try {
            UserDTO updatedUser = userService.logicalDeleteUser(id);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            logger.error("Error during logical delete", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint pour restaurer un utilisateur
    @PatchMapping("/{id}/restoreUser")
    public ResponseEntity<UserDTO> restoreUser(@PathVariable Long id) {
        try {
            UserDTO restoredUser = userService.restoreUser(id);
            return ResponseEntity.ok(restoredUser);
        } catch (Exception e) {
            logger.error("Error during user restoration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getDeletedUsers")
    public List<UserDTO> getDeletedUsers() {
        return userService.getDeletedUsers();
    }


    @PatchMapping("/{id}/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@PathVariable Long id) {
        return userService.resetPassword(id);
    }



}
