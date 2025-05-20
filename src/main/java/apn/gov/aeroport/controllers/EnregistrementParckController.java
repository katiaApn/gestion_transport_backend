package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.EnregistrementParckDTO;
import apn.gov.aeroport.services.EnregistrementParckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enregistrements-parck")
@RequiredArgsConstructor
public class EnregistrementParckController {

    private final EnregistrementParckService service;

    @PostMapping
    public ResponseEntity<EnregistrementParckDTO> save(@RequestBody EnregistrementParckDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<EnregistrementParckDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnregistrementParckDTO> findById(@PathVariable Long id) {
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

