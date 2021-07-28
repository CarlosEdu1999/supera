package com.example.demo;

import com.example.demo.model.Endereço;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.EndereçoRepository;
import com.example.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class DesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);

	}


}
