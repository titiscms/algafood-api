package com.algaworks.algafood.jpa;

import java.util.List;
import java.util.Optional;

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
		List<Cozinha> cozinhasAntes = cozinhaRepository.findAll();
		cozinhasAntes.forEach(cozinha -> {
			System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome());
		});
		
		// Salvando 2 cozinhas
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Japonesa");
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Brasileira");
		
		cozinhaRepository.save(cozinha1);
		cozinhaRepository.save(cozinha2);
		
		// Listar todas as cozinhas do banco de dados depois da inclusão
		List<Cozinha> cozinhasDepois = cozinhaRepository.findAll();
		cozinhasDepois.forEach(cozinha -> {
			System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome());
		});
		
		// Buscar cozinha com ID = 3
		Optional<Cozinha> cozinhaBuscado= cozinhaRepository.findById(3L);
		System.out.println("ID: " + cozinhaBuscado.get().getId() + " - Nome: " + cozinhaBuscado.get().getNome());
		
		Cozinha cozinha3 = new Cozinha();
		cozinha3.setId(cozinhaBuscado.get().getId());
		cozinha3.setNome("Russa");
		
		// atualizar cozinha com ID = 3
		Cozinha cozinhaAlterado = cozinhaRepository.save(cozinha3);
		System.out.println("ID: " + cozinhaAlterado.getId() + " - Nome: " + cozinhaAlterado.getNome());
		
		// Excluir cozinha com ID = 4
		Cozinha cozinhaDeletado = new Cozinha();
		cozinhaDeletado.setId(4L);
		
		cozinhaRepository.deleteById(cozinhaDeletado.getId());
		
		// Listar todos os cozinhas do banco de dados
		List<Cozinha> cozinhas = cozinhaRepository.findAll();
		cozinhas.forEach(cozinha -> {
			System.out.println("ID: " + cozinha.getId() + " - Nome: " + cozinha.getNome());
		});
	}
}
