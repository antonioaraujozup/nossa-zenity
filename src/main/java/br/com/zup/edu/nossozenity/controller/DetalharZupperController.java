package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.repository.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Zupper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DetalharZupperController {

    private final ZupperRepository repository;

    public DetalharZupperController(ZupperRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/zuppers/{id}")
    public ResponseEntity<?> detalhar(@PathVariable("id") Long id) {
        Zupper zupper = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        return ResponseEntity.ok(new ZupperResponse(zupper));
    }
}
