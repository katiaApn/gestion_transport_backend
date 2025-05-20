package apn.gov.aeroport.controllers;


import apn.gov.aeroport.domain.dto.TypeDeputeDTO;
import apn.gov.aeroport.services.TypeDeputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type-deputes")
@RequiredArgsConstructor
public class TypeDeputeController {

    private final TypeDeputeService service;

    @PostMapping
    public ResponseEntity<TypeDeputeDTO> save(@RequestBody TypeDeputeDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TypeDeputeDTO>> findAll() {
        //return ResponseEntity.ok(service.findAll());
        List<TypeDeputeDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeDeputeDTO> findById(@PathVariable Long id) {
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

