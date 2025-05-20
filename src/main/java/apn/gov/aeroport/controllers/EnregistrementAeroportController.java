package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.EnregistrementAeroportDTO;
import apn.gov.aeroport.domain.dto.EnregistrementAeroportFullDTO;
import apn.gov.aeroport.domain.dto.EnregistrementAeroportGroupesDto;
import apn.gov.aeroport.services.EnregistrementAeroportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enregistrements-aeroport")
@RequiredArgsConstructor
public class EnregistrementAeroportController {

    private final EnregistrementAeroportService service;

    @PostMapping
    public ResponseEntity<EnregistrementAeroportDTO> save(@RequestBody EnregistrementAeroportDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EnregistrementAeroportDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/getAllVols")
    public ResponseEntity<List<EnregistrementAeroportFullDTO>> getAllVols() {
        List<EnregistrementAeroportFullDTO> vols = service.findAllVols();
        return ResponseEntity.ok(vols);
    }


    @GetMapping("/findAllVolsDepartArriverToDay")
    public ResponseEntity<EnregistrementAeroportGroupesDto> findAllVolsDepartArriverToDay() {
        EnregistrementAeroportGroupesDto vols = service.findAllVolsDepartArriverToDay();
        return ResponseEntity.ok(vols);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnregistrementAeroportDTO> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
