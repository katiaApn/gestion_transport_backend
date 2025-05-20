package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.EnregistrementTransportDTO;
import apn.gov.aeroport.domain.dto.UserDTO;
import apn.gov.aeroport.services.EnregistrementTransportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enregistrements-transport")
@RequiredArgsConstructor
public class EnregistrementTransportController {

    private static final Logger logger = LoggerFactory.getLogger(EnregistrementTransportController.class);
    private final EnregistrementTransportService service;

    @PostMapping("/save")
    public ResponseEntity<EnregistrementTransportDTO> save(@RequestBody EnregistrementTransportDTO dto) {
        try {
            return ResponseEntity.ok(service.save(dto));
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement du transport", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EnregistrementTransportDTO>> findAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de tous les enregistrements", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<EnregistrementTransportDTO> findById(@PathVariable Long id) {
        try {
            return service.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération de l'enregistrement avec l'ID: " + id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'enregistrement avec l'ID: " + id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/{id}/logicalDelete")
    public ResponseEntity<EnregistrementTransportDTO> logicalDelete(@PathVariable Long id) {
        try {
            EnregistrementTransportDTO updatedEnregistrement = service.logicalDeleteEnregistrement(id);
            return ResponseEntity.ok(updatedEnregistrement);
        } catch (Exception e) {
            logger.error("Error during logical delete", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
