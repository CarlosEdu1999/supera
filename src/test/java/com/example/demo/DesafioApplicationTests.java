package com.example.demo;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.example.demo.builders.PessoaBuilder;
import com.example.demo.controller.PessoaController;
import com.example.demo.model.Endereço;
import com.example.demo.model.Pessoa;
import com.example.demo.repository.PessoaRepository;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.TrueCondition;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DesafioApplicationTests {

	PessoaBuilder builder = new PessoaBuilder();

	@Mock
	private PessoaRepository pessoaRepository;
	@InjectMocks
	private PessoaController pessoaController;

	@Test
	void contextLoads() {
	}

	@Test
	void whenPersonInformedThenShouldBeCreated() {
		//given
		Pessoa pessoa = builder.toPessoa();
		//when

		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
		//then
		ResponseEntity<Pessoa> pessoaCriada = pessoaController.postPessoa(pessoa);

		assertEquals(pessoa.getId(), pessoaCriada.getBody().getId());
		assertEquals(pessoa.getNome(), pessoaCriada.getBody().getNome());
		assertEquals(pessoa.getDataDeNascimento(), pessoaCriada.getBody().getDataDeNascimento());
	}

	@Test
	void whenPersonInformedThenShouldBeAltered() {
		//given
		Pessoa pessoa = builder.toPessoa();
		//when
		when(pessoaRepository.findByNome(pessoa.getNome())).thenReturn(Optional.of(pessoa));
		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
		//then
		ResponseEntity<Pessoa> pessoaCriada = pessoaController.putPessoa(pessoa);

		assertEquals(pessoa.getId(), pessoaCriada.getBody().getId());
		assertEquals(pessoa.getNome(), pessoaCriada.getBody().getNome());
		assertEquals(pessoa.getDataDeNascimento(), pessoaCriada.getBody().getDataDeNascimento());
	}

	@Test
	void whenMainAddressInformedShouldBeChanged() {
		//given
		Pessoa pessoa = builder.toPessoa();
		Endereço endereço = new Endereço("alameda dourada", 1231424, 121, "pinheiros");
		pessoaRepository.save(pessoa);

		//when
		when(pessoaRepository.getById(pessoa.getId())).thenReturn(pessoa);
		when(pessoaRepository.findByNome(pessoa.getNome())).thenReturn(Optional.of(pessoa));
		pessoa.setEndereçoSecundário(pessoa.getEndereçoPrincipal());
		pessoa.setEndereçoPrincipal(endereço);
		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
		//then
		ResponseEntity<Pessoa> pessoaAtualizada = pessoaController.putEndereçoPessoa(endereço, pessoa.getId());

		assertEquals(pessoaAtualizada.getBody().getEndereçoPrincipal(), endereço);
		assertEquals(pessoaAtualizada.getBody().getEndereçoSecundário(), pessoa.getEndereçoSecundário());
	}

	@Test
	void whenListPersonsIsCalledThenReturnAnEmptyListOfPersons() {
		when(pessoaRepository.findAll()).thenReturn(Collections.emptyList());

		ResponseEntity<List<Pessoa>> listaEncontrada = pessoaController.getAll();

		assertThat(listaEncontrada.getBody(), is(empty()));
	}

	@Test
	void whenListPersonsIsCalledThenReturnAListOfPerson() {
		Pessoa pessoa = builder.toPessoa();
		pessoaController.postPessoa(pessoa);


		when(pessoaRepository.findAll()).thenReturn(Collections.singletonList(pessoa));

		assertEquals(pessoaRepository.findAll().contains(pessoa), true);

	}

	@Test
	void whenAddressInformedShouldChangePerson() {
		Pessoa pessoa = builder.toPessoa();
		Endereço endereço = new Endereço("alameda dourada", 1231424, 121, "pinheiros");
		pessoaRepository.save(pessoa);
		//when
		when(pessoaRepository.getById(pessoa.getId())).thenReturn(pessoa);
		when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

		//then
		ResponseEntity<Pessoa> pessoaAtualizada = pessoaController.postEndereçoPessoa(endereço, pessoa.getId());

		assertEquals(pessoaAtualizada.getBody().getEndereçoPrincipal(), endereço);
		assertEquals(pessoaAtualizada.getBody().getEndereçoSecundário(), pessoa.getEndereçoSecundário());

	}

}