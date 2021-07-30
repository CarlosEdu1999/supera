package com.example.demo.repository;

import com.example.demo.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
    Optional<Pessoa> findByNome(String nome);
    Pessoa getByNome(String nome);

}
