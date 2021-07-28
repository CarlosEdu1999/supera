package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.message.StringFormattedMessage;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="endereço")
public class Endereço {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;

    @Column(name="logradouro")
    private String logradouro;

    @Column(name = "cep")
    private Integer cep;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "cidade")
    private String cidade;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Endereço(String logradouro, Integer cep, Integer numero, String cidade) {
        this.logradouro = logradouro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
    }

    public Endereço() {
    }
}
