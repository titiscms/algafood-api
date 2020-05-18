package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;

public class InclusaoCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		// lista todas as cozinhas do banco de dados
		List<Cozinha> cozinhasAntes = cozinhaRepository.listar();
		cozinhasAntes.forEach(cozinha -> System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome()));

		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		
		// incluindo as cozinhas no banco de dados
		cozinha1 = cozinhaRepository.salvar(cozinha1);
		System.out.printf("%d - %s\n" , cozinha1.getId(), cozinha1.getNome());
		cozinha2 = cozinhaRepository.salvar(cozinha2);
		System.out.printf("%d - %s\n" , cozinha2.getId(), cozinha2.getNome());
		
		// lista todas as cozinhas do banco de dados		
		List<Cozinha> cozinhasDepois = cozinhaRepository.listar();
		cozinhasDepois.forEach(cozinha -> System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome()));
	}
}
