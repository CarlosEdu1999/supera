package com.example.demo.controller;

import com.example.demo.model.Endereço;
import com.example.demo.repository.EndereçoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EndereçoController {
    @Autowired
    EndereçoRepository repository;

    @GetMapping("/endereços")
    public ResponseEntity<List<Endereço>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }
    @GetMapping("/endereços/{id}")
    public ResponseEntity<Endereço> getById(@PathVariable long id){
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/endereços")
    public ResponseEntity<Endereço> postEndereço(@RequestBody Endereço endereço){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(endereço));
    }

}
