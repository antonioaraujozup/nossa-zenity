package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.zupper.Zupper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ZupperResponse {

    private String nome;
    private String cargo;
    private LocalDate dataAdmissao;
    private List<KudoResponse> kudosRecebidos;
    private List<HabilidadeResponse> habilidades;
    private List<CertificadoResponse> certificados;

    public ZupperResponse(Zupper zupper) {
        this.nome = zupper.getNome();
        this.cargo = zupper.getCargo();
        this.dataAdmissao = zupper.getDataAdmissao();
        this.kudosRecebidos = zupper.getKudosRecebidos().stream()
                .map(k -> new KudoResponse(k))
                .collect(Collectors.toList());
        this.habilidades = zupper.getHabilidades().stream()
                .map(h -> new HabilidadeResponse(h))
                .collect(Collectors.toList());
        this.certificados = zupper.getCertificados().stream()
                .map(c -> new CertificadoResponse(c))
                .collect(Collectors.toList());
    }

    public String getNome() {
        return nome;
    }

    public String getCargo() {
        return cargo;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public List<KudoResponse> getKudosRecebidos() {
        return kudosRecebidos;
    }

    public List<HabilidadeResponse> getHabilidades() {
        return habilidades;
    }

    public List<CertificadoResponse> getCertificados() {
        return certificados;
    }
}
