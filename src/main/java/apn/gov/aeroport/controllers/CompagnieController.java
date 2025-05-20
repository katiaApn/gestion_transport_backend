package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.CompanieDTO;
import apn.gov.aeroport.services.CompagnieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compagnies")
@RequiredArgsConstructor
public class CompagnieController {

    private final CompagnieService compagnieService;

    @PostMapping
    public ResponseEntity<CompanieDTO> save(@RequestBody CompanieDTO dto) {
        return ResponseEntity.ok(compagnieService.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CompanieDTO>> findAll() {
        return ResponseEntity.ok(compagnieService.findAll());
    }

    @GetMapping("/getAllValid")
    public ResponseEntity<List<CompanieDTO>> findAllValid() {
        return ResponseEntity.ok(compagnieService.findAllValid());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CompanieDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(compagnieService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        compagnieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

