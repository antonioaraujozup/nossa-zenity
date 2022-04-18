package br.com.zup.edu.nossozenity.zupper;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Kudo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoKudo nome;

    @Column(nullable = false)
    private LocalDateTime criadoEm= LocalDateTime.now();

    @ManyToOne(optional = false)
    private Zupper recebido;

    @ManyToOne(optional = false)
    private Zupper enviado;

    public Kudo(TipoKudo nome, LocalDateTime criadoEm, Zupper recebido) {
        this.nome = nome;
        this.criadoEm = criadoEm;
        this.recebido = recebido;
    }

    /**
     * @deprecated construtor de uso exclusivo para o hibernate
     */
    @Deprecated
    public Kudo() {
    }

    public Long getId() {
        return id;
    }

    public TipoKudo getNome() {
        return nome;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public String getNomeZupperEnviado() {
        return this.enviado.getNome();
    }
}
