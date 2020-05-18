package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class AtualizarCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext =new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		// lacaliza cozinha com ID = 1 ANTES de alterada
		Cozinha cozinhaAntes = cadastroCozinha.buscar(1L);
		System.out.println("ID: " + cozinhaAntes.getId() + " - Nome: " + cozinhaAntes.getNome());
		
		Cozinha cozinhaAlterada = new Cozinha();
		cozinhaAlterada.setId(1L);
		cozinhaAlterada.setNome("Brasileira");
		
		// localiza cozinha com ID = 1 DEPOIS de alterada
		cozinhaAlterada = cadastroCozinha.salvar(cozinhaAlterada);
		System.out.println("ID: " + cozinhaAlterada.getId() + " - Nome: " + cozinhaAlterada.getNome());
	}

}
