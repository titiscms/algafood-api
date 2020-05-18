package com.algaworks.algafood.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;

public class AtualizarCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext =new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		// lacaliza cozinha com ID = 1 ANTES de alterada
		Cozinha cozinhaAntes = cozinhaRepository.buscar(1L);
		System.out.println("ID: " + cozinhaAntes.getId() + " - Nome: " + cozinhaAntes.getNome());
		
		Cozinha cozinhaParaAlterar = new Cozinha();
		cozinhaParaAlterar.setId(1L);
		cozinhaParaAlterar.setNome("Brasileira");
		
		// localiza cozinha com ID = 1 DEPOIS de alterada
		cozinhaParaAlterar = cozinhaRepository.salvar(cozinhaParaAlterar);
		System.out.println("ID: " + cozinhaParaAlterar.getId() + " - Nome: " + cozinhaParaAlterar.getNome());
	}

}
