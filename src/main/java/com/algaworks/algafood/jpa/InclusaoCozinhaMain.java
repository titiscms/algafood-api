package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class InclusaoCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);
		
		// lista todas as cozinhas do banco de dados
		List<Cozinha> cozinhasAntes = cadastroCozinha.listar();
		cozinhasAntes.forEach(cozinha -> System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome()));

		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		
		// incluindo as cozinhas no banco de dados
		cozinha1 = cadastroCozinha.salvar(cozinha1);
		System.out.printf("%d - %s\n" , cozinha1.getId(), cozinha1.getNome());
		cozinha2 = cadastroCozinha.salvar(cozinha2);
		System.out.printf("%d - %s\n" , cozinha2.getId(), cozinha2.getNome());
		
		// lista todas as cozinhas do banco de dados		
		List<Cozinha> cozinhasDepois = cadastroCozinha.listar();
		cozinhasDepois.forEach(cozinha -> System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome()));
	}
}
