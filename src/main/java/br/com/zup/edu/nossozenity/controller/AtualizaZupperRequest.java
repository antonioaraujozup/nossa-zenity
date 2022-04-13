package br.com.zup.edu.nossozenity.controller;

import javax.validation.constraints.NotBlank;

public class AtualizaZupperRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String cargo;

    public AtualizaZupperRequest(String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }
}
