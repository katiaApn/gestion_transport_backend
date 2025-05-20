package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.PeriodeLegislatifDTO;
import apn.gov.aeroport.services.PeriodeLegislatifService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/periodes-legislatifs")
@RequiredArgsConstructor
public class PeriodeLegislatifController {

    private final PeriodeLegislatifService service;

    @PostMapping
    public ResponseEntity<PeriodeLegislatifDTO> save(@RequestBody PeriodeLegislatifDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PeriodeLegislatifDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/getValid")
    public ResponseEntity<PeriodeLegislatifDTO> findValid() {
        return ResponseEntity.ok(service.getActivePeriodeLegislatif());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeriodeLegislatifDTO> findById(@PathVariable Long id) {
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
