package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.repository.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Zupper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ListarHabilidadesZupperController {

    private final ZupperRepository repository;

    public ListarHabilidadesZupperController(ZupperRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/zuppers/{id}/habilidades")
    public ResponseEntity<?> listarHabilidades(@PathVariable("id") Long id) {
        Zupper zupper = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<HabilidadeResponse> response = zupper.getHabilidades().stream()
                .map(HabilidadeResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
