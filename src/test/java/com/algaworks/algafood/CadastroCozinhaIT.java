package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIT {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void shouldAtribuirId_WhenCadastrarCozinhaComDadosCorretos() {
		// scenery
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		// action
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
		
		// validation
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void shouldFalhar_WhenCadastrarCozinhaSemNome() {
		// scenery
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		
		// action
		novaCozinha = cadastroCozinha.salvar(novaCozinha);
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void shouldFalhar_WhenExcluirCozinhaEmUso() {
		// action
		cadastroCozinha.remover(1L);
	}
	
	@Test(expected = EntidadeNaoEncontradaException.class)
	public void shouldFalhar_WhenExcluirCozinhaInexistente() {
		// action
		cadastroCozinha.remover(100L);
	}
}
