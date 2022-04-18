package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.zupper.Habilidade;
import br.com.zup.edu.nossozenity.zupper.NivelHabilidade;

public class HabilidadeResponse {

    private String nome;
    private NivelHabilidade nivel;

    public HabilidadeResponse(Habilidade habilidade) {
        this.nome = habilidade.getNome();
        this.nivel = habilidade.getNivel();
    }

    public String getNome() {
        return nome;
    }

    public NivelHabilidade getNivel() {
        return nivel;
    }
}
