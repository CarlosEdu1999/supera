package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "dataDeNascimento")
    private LocalDate dataDeNascimento;

    @ManyToOne
    @JoinColumn(name = "endereço", referencedColumnName = "id")
    private Endereço endereçoPrincipal;

    @ManyToOne
    @JoinColumn(name = "endereço2", referencedColumnName = "id")
    private Endereço endereçoSecundário;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Endereço getEndereçoPrincipal() {
        return endereçoPrincipal;
    }

    public void setEndereçoPrincipal(Endereço endereçoPrincipal) {
        this.endereçoPrincipal = endereçoPrincipal;
    }

    public Endereço getEndereçoSecundário() {
        return endereçoSecundário;
    }

    public void setEndereçoSecundário(Endereço endereçoSecundário) {
        this.endereçoSecundário = endereçoSecundário;
    }
}
