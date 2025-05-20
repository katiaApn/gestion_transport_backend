package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.DeputeDTO;
import apn.gov.aeroport.domain.dto.DeputeEnregistrementDTO;
import apn.gov.aeroport.services.DeputeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deputes")
@RequiredArgsConstructor
@Slf4j
public class DeputeController {

    private final DeputeService deputeService;

    @PostMapping("/save")
    public ResponseEntity<DeputeDTO> save(@RequestBody DeputeDTO dto) {
        log.info("Création ou modification d’un député");
        return ResponseEntity.ok(deputeService.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DeputeDTO>> findAll() {
        return ResponseEntity.ok(deputeService.findAll());
    }

    @GetMapping("/findAllDeputeEnregistrement")
    public ResponseEntity<List<DeputeEnregistrementDTO>> findAllDeputeEnregistrement() {
        return ResponseEntity.ok(deputeService.findAllDeputeEnregistrement());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<DeputeDTO> findById(@PathVariable Long id) {
        return deputeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deputeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
