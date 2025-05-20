package apn.gov.aeroport.controllers;

import apn.gov.aeroport.domain.dto.ProtocoleDTO;
import apn.gov.aeroport.services.ProtocoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/protocoles")
@RequiredArgsConstructor
@Slf4j
public class ProtocoleController {

    private final ProtocoleService protocoleService;

    @PostMapping("/save")
    public ResponseEntity<ProtocoleDTO> save(@RequestBody ProtocoleDTO dto) {
        log.info("Création ou modification d’un protocole");
        return ResponseEntity.ok(protocoleService.save(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProtocoleDTO>> findAll() {
        return ResponseEntity.ok(protocoleService.findAll());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProtocoleDTO> findById(@PathVariable Long id) {
        return protocoleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        protocoleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
