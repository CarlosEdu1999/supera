package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "dataDeNascimento")
    private LocalDate dataDeNascimento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereço", referencedColumnName = "id")
    private Endereço endereçoPrincipal;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "endereço2", referencedColumnName = "id")
    private Endereço endereçoSecundário;

    public Pessoa(String nome, LocalDate dataDeNascimento, Endereço endereçoPrincipal, Endereço endereçoSecundário) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.endereçoPrincipal = endereçoPrincipal;
        this.endereçoSecundário = endereçoSecundário;
    }
}
