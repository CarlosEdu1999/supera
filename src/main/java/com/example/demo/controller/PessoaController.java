package com.example.demo.controller;

import com.example.demo.model.Endereço;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.EndereçoRepository;
import com.example.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PessoaController {

    @Autowired
    PessoaRepository repository;
    @Autowired
    EndereçoRepository repository2;

    public Boolean verifyIfAlreadyRegistered(Pessoa pessoa){
      Optional<Pessoa> optional =  repository.findByNome(pessoa.getNome());
      if (optional.isPresent()){
          return true;
      }else {
            return false;
        }
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping("/pessoas/id/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable long id)  {
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/pessoas/nome/{nome}")
    public ResponseEntity<Pessoa> getByNome(@PathVariable String nome)  {
        return repository.findByNome(nome).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping("/pessoas")
    public ResponseEntity<Pessoa> postPessoa(@RequestBody Pessoa pessoa) {
        boolean conditional = verifyIfAlreadyRegistered(pessoa);
        if (conditional){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(repository.getById(pessoa.getId()));
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pessoa));
        }
    }

    @PutMapping("/pessoas")
    public ResponseEntity<Pessoa> putPessoa(@RequestBody Pessoa pessoa) {
        boolean conditional = verifyIfAlreadyRegistered(pessoa);
        if (conditional){
            repository.delete(repository.getByNome(pessoa.getNome()));
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(pessoa));
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pessoa));
        }
    }

    @GetMapping("/pessoas/{id}/endereços")
    public ResponseEntity<List<Endereço>> getAllEndereços(@PathVariable long id) {

        return ResponseEntity.status(HttpStatus.OK).body(List.of(repository.getById(id).getEndereçoPrincipal(), repository.getById(id).getEndereçoSecundário()));
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
        return ResponseEntity.status(HttpStatus.OK).body(repository.saveAll(pessoas));
    }

    //Informar endereço principal
    @PutMapping("/pessoas/{id}/endereços")
    public ResponseEntity<Pessoa> putEndereçoPessoa(@RequestBody Endereço endereço, @PathVariable long id) {
        Pessoa pessoa = repository.getById(id);
        boolean conditional = verifyIfAlreadyRegistered(pessoa);
        if (conditional) {
            pessoa.setEndereçoSecundário(pessoa.getEndereçoPrincipal());
            pessoa.setEndereçoPrincipal(endereço);
            return ResponseEntity.status(HttpStatus.OK).body(repository.save(pessoa));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Pessoa());
        }

    }
    @PostMapping("/pessoas/{id}/endereços")
    public ResponseEntity<Pessoa> postEndereçoPessoa(@RequestBody Endereço endereço, @PathVariable long id) {
       Pessoa pessoa =  repository.getById(id);
       pessoa.setEndereçoPrincipal(endereço);
       return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(pessoa));

    }
    @DeleteMapping("/pessoas/{id}")
    public void deleteById(@PathVariable long id) {
        try {
            repository.deleteById(id);
            ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            ResponseEntity.notFound().build();

        }


    }
}