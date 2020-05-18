package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class RemoverCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		// verifica lista de cozinhas ANTES da exclusão
		List<Cozinha> cozinhasAntes = cadastroCozinha.listar();
		cozinhasAntes.forEach(c -> System.out.println("ID: " + c.getId() + " - Nome: " + c.getNome()));
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		
		cadastroCozinha.remover(cozinha);
		
		// verifica lista de cozinhas DEPOIS da exclusão
		List<Cozinha> cozinhasDepois = cadastroCozinha.listar();
		cozinhasDepois.forEach(c -> System.out.println("ID: " + c.getId() + " - Nome: " + c.getNome()));
	}
}
