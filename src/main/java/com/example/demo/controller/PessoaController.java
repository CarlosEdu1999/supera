package com.example.demo.controller;

import com.example.demo.model.Endereço;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.EndereçoRepository;
import com.example.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PessoaController {

    @Autowired
    PessoaRepository repository;
    @Autowired
    EndereçoRepository repository2;

    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable long id) {
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pessoas")
    public ResponseEntity<Pessoa> postPessoa(@RequestBody Pessoa pessoa) {
        if (pessoa.getEndereçoPrincipal()!= null){repository2.save(pessoa.getEndereçoPrincipal() );}
        if (pessoa.getEndereçoSecundário()!= null){repository2.save(pessoa.getEndereçoSecundário());}
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pessoa));
    }

    @PutMapping("/pessoas")
    public ResponseEntity<Pessoa> putPessoa(@RequestBody Pessoa pessoa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pessoa));
    }

    @GetMapping("/pessoas/{id}/endereços")
    public ResponseEntity<List<Endereço>> getAllEndereços(@PathVariable long id) {

        return ResponseEntity.status(HttpStatus.OK).body(List.of(repository.getById(id).getEndereçoPrincipal(), repository.getById(id).getEndereçoSecundário()));
    }

    @PostMapping("/pessoas/{id}/endereços")
    public ResponseEntity<Pessoa> postEndereçoPessoa(@RequestBody Endereço endereço, @PathVariable long id) {
        if (repository.getById(id).getEndereçoPrincipal() == null) {
            repository.getById(id).setEndereçoPrincipal(endereço);
        } else {
            repository.getById(id).setEndereçoSecundário(endereço);
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(repository.getById(id));
    }
    @GetMapping("/load")
    public ResponseEntity<List<Pessoa>> load() {
        Endereço endereço1 = new Endereço("rua silvio fernandes", 38406196, 424, "uberlandia");
        Endereço endereço2 = new Endereço("rua batista", 3842332, 4224, "ituiutaba");
        Endereço endereço3 = new Endereço("rua touro", 3842321, 224, "goiania");
        Endereço endereço4 = new Endereço("rua astronauta", 324521, 287, "jurassic");
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        Pessoa pessoa1 = new Pessoa("Carlos", LocalDate.of(1999, 05, 19), endereço1, endereço2);
        Pessoa pessoa2 = new Pessoa("Joao", LocalDate.of(1989, 03, 12), endereço4, endereço3);
        pessoas.add(pessoa1);
        pessoas.add(pessoa2);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(repository.saveAll(pessoas));
    }


    @PutMapping("/pessoas/{id}/endereços")
    public ResponseEntity<Pessoa> putEndereçoPessoa(@RequestBody Endereço endereço, @PathVariable long id) {
        Pessoa pessoa =  repository.getById(id);
        pessoa.setEndereçoPrincipal(endereço);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.saveAndFlush(pessoa));
    }

}