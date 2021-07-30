package com.example.demo.builders;

import com.example.demo.model.Endereço;
import com.example.demo.model.Pessoa;
import lombok.*;

import java.time.LocalDate;

@Builder(builderClassName = "Builder",toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class PessoaBuilder {

    @lombok.Builder.Default
    private long id = 1L;

    @lombok.Builder.Default
    private  String nome = "Marcos";

    @lombok.Builder.Default    private LocalDate dataDeNascimento = LocalDate.of(1987, 03, 22);

    @lombok.Builder.Default    private Endereço endereçoPrincipal = new Endereço("Rua urano", 12341241, 235, "itu");

    @lombok.Builder.Default    private Endereço endereçoSecundário = new Endereço("Rua Sirius", 1234124124, 2421, "itu");

    public Pessoa toPessoa(){
        return new Pessoa(nome,
                dataDeNascimento,
                endereçoPrincipal,
                endereçoSecundário);
    }
}
