package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;

public class CrudCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

		// Listar todas as cozinhas do banco de dados inicial
		List<Cozinha> cozinhasAntes = cozinhaRepository.listar();
		cozinhasAntes.forEach(cozinha -> {
			System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome());
		});
		
		// Salvando 2 cozinhas
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Japonesa");
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Brasileira");
		
		cozinhaRepository.salvar(cozinha1);
		cozinhaRepository.salvar(cozinha2);
		
		// Listar todas as cozinhas do banco de dados depois da inclusão
		List<Cozinha> cozinhasDepois = cozinhaRepository.listar();
		cozinhasDepois.forEach(cozinha -> {
			System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome());
		});
		
		// Buscar cozinha com ID = 3
		Cozinha cozinhaBuscado= cozinhaRepository.buscar(3L);
		System.out.println("ID: " + cozinhaBuscado.getId() + " - Nome: " + cozinhaBuscado.getNome());
		
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setId(cozinhaBuscado.getId());
		cozinha3.setNome("Russa");
		
		// atualizar cozinha com ID = 3
		Cozinha cozinhaAlterado = cozinhaRepository.salvar(cozinha3);
		System.out.println("ID: " + cozinhaAlterado.getId() + " - Nome: " + cozinhaAlterado.getNome());
		
		// Excluir cozinha com ID = 4
		Cozinha cozinhaDeletado = new Cozinha();
		cozinhaDeletado.setId(4L);
		
		cozinhaRepository.remover(cozinhaDeletado.getId());
		
		// Listar todos os cozinhas do banco de dados
		List<Cozinha> cozinhas = cozinhaRepository.listar();
		cozinhas.forEach(cozinha -> {
			System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome());
		});
	}
}
