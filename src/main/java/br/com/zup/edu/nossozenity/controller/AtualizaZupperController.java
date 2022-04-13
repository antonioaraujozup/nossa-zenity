package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.repository.ZupperRepository;
import br.com.zup.edu.nossozenity.zupper.Zupper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class AtualizaZupperController {

    private final ZupperRepository repository;

    public AtualizaZupperController(ZupperRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PatchMapping("/zuppers/{id}")
    public ResponseEntity<?> atualiza(@PathVariable Long id, @RequestBody @Valid AtualizaZupperRequest request) {
        Zupper zupper = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        zupper.setNome(request.getNome());
        zupper.setCargo(request.getCargo());

        repository.save(zupper);

        return ResponseEntity.noContent().build();
    }
}
