package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.repository.CidadeRepository;
import com.algaworks.algafood.domain.model.repository.EstadoRepository;

public class CrudCidadeMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

		// Listar todas as cidade do banco de dados inicial
		List<Cidade> cidadesAntes = cidadeRepository.listar();
		cidadesAntes.forEach(cidade -> {
			System.out.println("ID: " + cidade.getId() + " - Nome: " + cidade.getNome() + " - Estado: " + cidade.getEstado().getNome());
		});
		
		// Salvando 2 cidades
		Cidade cidade1 = new Cidade();
		cidade1.setNome("Campos do Jordão");
		Estado estado1 = estadoRepository.buscar(2L);
		cidade1.setEstado(estado1);
		
		Cidade cidade2 = new Cidade();
		cidade2.setNome("Taboão da Serra");
		Estado estado2 = estadoRepository.buscar(2L);
		cidade2.setEstado(estado2);
		
		cidadeRepository.salvar(cidade1);
		cidadeRepository.salvar(cidade2);
		
		// Listar todas as cidades do banco de dados depois da inclusão
		List<Cidade> cidadesDepois = cidadeRepository.listar();
		cidadesDepois.forEach(cidade -> {
			System.out.println("ID: " + cidade.getId() + " - Nome: " + cidade.getNome() + " - Estado: " + cidade.getEstado().getNome());
		});
		
		// Buscar cidade com ID = 6
		Cidade cidadeBuscado = cidadeRepository.buscar(6L);
		System.out.println("ID: " + cidadeBuscado.getId() + " - Nome: " + cidadeBuscado.getNome() + 
				" - Estado: " + cidadeBuscado.getEstado().getNome());
		
		Cidade cidade3 = new Cidade();
		cidade3.setId(cidadeBuscado.getId());
		cidade3.setNome("Atibaia");
		Estado estado3 = estadoRepository.buscar(cidadeBuscado.getEstado().getId());
		cidade3.setEstado(estado3);
		
		// atualizar cidade com ID = 6
		Cidade cidadeAlterado = cidadeRepository.salvar(cidade3);
		System.out.println("ID: " + cidadeAlterado.getId() + " - Nome: " + cidadeAlterado.getNome() + 
				" - Estado: " + cidadeAlterado.getEstado().getNome());
		
		// Excluir cidade com ID = 7
		Cidade cidadeDeletado = new Cidade();
		cidadeDeletado.setId(7L);
		
		cidadeRepository.remover(cidadeDeletado.getId());
		
		// Listar todas as cidade do banco de dados
		List<Cidade> cidades = cidadeRepository.listar();
		cidades.forEach(cidade -> {
			System.out.println("ID: " + cidade.getId() + " - Nome: " + cidade.getNome() + " - Estado: " + cidade.getEstado().getNome());
		});
	}
}
