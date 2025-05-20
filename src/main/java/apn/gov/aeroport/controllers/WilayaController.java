package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.WilayaDTO;
import apn.gov.aeroport.services.WilayaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wilayas")
@RequiredArgsConstructor
public class WilayaController {

    private final WilayaService service;

    @PostMapping
    public ResponseEntity<WilayaDTO> save(@RequestBody WilayaDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<WilayaDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WilayaDTO> findById(@PathVariable Long id) {
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
