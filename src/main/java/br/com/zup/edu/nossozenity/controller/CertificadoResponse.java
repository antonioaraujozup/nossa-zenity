package br.com.zup.edu.nossozenity.controller;

import br.com.zup.edu.nossozenity.zupper.Certificado;
import br.com.zup.edu.nossozenity.zupper.TipoCertificado;

public class CertificadoResponse {

    private String nome;
    private String instituicaoEmissora;
    private String link;
    private TipoCertificado tipo;

    public CertificadoResponse(Certificado certificado) {
        this.nome = certificado.getNome();
        this.instituicaoEmissora = certificado.getInstituicaoEmissora();
        this.link = certificado.getLink();
        this.tipo = certificado.getTipo();
    }

    public String getNome() {
        return nome;
    }

    public String getInstituicaoEmissora() {
        return instituicaoEmissora;
    }

    public String getLink() {
        return link;
    }

    public TipoCertificado getTipo() {
        return tipo;
    }
}
