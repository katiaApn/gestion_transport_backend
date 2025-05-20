package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.AeroportDTO;
import apn.gov.aeroport.services.AeroportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aeroports")
@RequiredArgsConstructor
public class AeroportController {

    private final AeroportService aeroportService;

    @PostMapping
    public ResponseEntity<AeroportDTO> save(@RequestBody AeroportDTO dto) {
        return ResponseEntity.ok(aeroportService.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AeroportDTO>> findAll() {
        return ResponseEntity.ok(aeroportService.findAll());
    }

    @GetMapping("/getAllValid")
    public ResponseEntity<List<AeroportDTO>> findAllValid() {
        return ResponseEntity.ok(aeroportService.findAllValid());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AeroportDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(aeroportService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aeroportService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

