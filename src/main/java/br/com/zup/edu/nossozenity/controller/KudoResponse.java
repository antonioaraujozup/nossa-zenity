package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.zupper.Kudo;
import br.com.zup.edu.nossozenity.zupper.TipoKudo;

import java.time.LocalDateTime;

public class KudoResponse {

    private TipoKudo nome;
    private LocalDateTime criadoEm;
    private String nomeZupperEnviado;

    public KudoResponse(Kudo kudo) {
        this.nome = kudo.getNome();
        this.criadoEm = kudo.getCriadoEm();
        this.nomeZupperEnviado = kudo.getNomeZupperEnviado();
    }

    public TipoKudo getNome() {
        return nome;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public String getNomeZupperEnviado() {
        return nomeZupperEnviado;
    }
}
