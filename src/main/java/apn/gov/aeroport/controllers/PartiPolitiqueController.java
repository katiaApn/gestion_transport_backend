package apn.gov.aeroport.controllers;
import apn.gov.aeroport.domain.dto.PartiPolitiqueDTO;
import apn.gov.aeroport.services.PartiPolitiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partis-politiques")
@RequiredArgsConstructor
public class PartiPolitiqueController {

    private final PartiPolitiqueService service;

    @PostMapping
    public ResponseEntity<PartiPolitiqueDTO> save(@RequestBody PartiPolitiqueDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PartiPolitiqueDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartiPolitiqueDTO> findById(@PathVariable Long id) {
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
