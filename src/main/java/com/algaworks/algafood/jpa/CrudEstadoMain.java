package com.algaworks.algafood.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class CrudEstadoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

		// Listar todos os estado do banco de dados inicial
		List<Estado> estadosAntes = estadoRepository.findAll();
		estadosAntes.forEach(estado -> {
			System.out.println("ID: " + estado.getId() + " - Nome: " + estado.getNome());
		});
		
		// Salvando 2 estados
		Estado estado1 = new Estado();
		estado1.setNome("Rio de Janeiro");
		
		Estado estado2 = new Estado();
		estado2.setNome("Paraná");
		
		estadoRepository.save(estado1);
		estadoRepository.save(estado2);
		
		// Listar todos os estado do banco de dados depois da inclusão
		List<Estado> estadosDepois = estadoRepository.findAll();
		estadosDepois.forEach(estado -> {
			System.out.println("ID: " + estado.getId() + " - Nome: " + estado.getNome());
		});
		
		// Buscar restaurate com ID = 4
		Optional<Estado> estadoBuscado= estadoRepository.findById(4L);
		System.out.println("ID: " + estadoBuscado.get().getId() + " - Nome: " + estadoBuscado.get().getNome());
		
		Estado estado3 = new Estado();
		estado3.setId(estadoBuscado.get().getId());
		estado3.setNome("Santa Catarina");
		
		// atualizar estado com ID = 4
		Estado estadoAlterado = estadoRepository.save(estado3);
		System.out.println("ID: " + estadoAlterado.getId() + " - Nome: " + estadoAlterado.getNome());
		
		// Excluir estado com ID = 5
		Estado estadoDeletado = new Estado();
		estadoDeletado.setId(5L);
		
		estadoRepository.deleteById(estadoDeletado.getId());
		
		// Listar todos os estado do banco de dados
		List<Estado> estados = estadoRepository.findAll();
		estados.forEach(estado -> {
			System.out.println("ID: " + estado.getId() + " - Nome: " + estado.getNome());
		});
	}
}
