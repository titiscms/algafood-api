package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.repository.PermissaoRepository;

public class CrudPermissaoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		PermissaoRepository permissaoRepository = applicationContext.getBean(PermissaoRepository.class);

		// Listar todas as permissões do banco de dados inicial
		List<Permissao> permissaosAntes = permissaoRepository.findAll();
		permissaosAntes.forEach(permissao -> {
			System.out.println("ID: " + permissao.getId() + " - Nome: " + permissao.getNome() + 
					" - Descrição: " + permissao.getDescricao());
		});
		
		// Salvando 2 permissões
		Permissao permissao1 = new Permissao();
		permissao1.setNome("DELETAR_COZINHAS");
		permissao1.setDescricao("Permite deletar cozinhas");
		
		Permissao permissao2 = new Permissao();
		permissao2.setNome("DELETAR_RESTAURANTES");
		permissao2.setDescricao("Permite deletar restaurantes");
		
		permissaoRepository.save(permissao1);
		permissaoRepository.save(permissao2);
		
		// Listar todas as permissões do banco de dados depois da inclusão
		List<Permissao> permissaosDepois = permissaoRepository.findAll();
		permissaosDepois.forEach(permissao -> {
			System.out.println("ID: " + permissao.getId() + " - Nome: " + permissao.getNome() + 
					" - Descrição: " + permissao.getDescricao());
		});
		
		// Buscar permissao com ID = 3
		Permissao permissaoBuscado = permissaoRepository.getOne(3L);
		System.out.println("ID: " + permissaoBuscado.getId() + " - Nome: " + permissaoBuscado.getNome() + 
				" - Descrição: " + permissaoBuscado.getDescricao());
		
		Permissao permissao3 = new Permissao();
		permissao3.setId(permissaoBuscado.getId());
		permissao3.setNome("EDITAR_RESTAURANTES");
		permissao3.setDescricao("Permite editar restaurantes");
		
		// atualizar permissao com ID = 3
		Permissao permissaoAlterado = permissaoRepository.save(permissao3);
		System.out.println("ID: " + permissaoAlterado.getId() + " - Nome: " + permissaoAlterado.getNome() + 
				" - Descrição: " + permissaoAlterado.getDescricao());
		
		// Excluir permissao com ID = 4
		Permissao permissaoDeletado = new Permissao();
		permissaoDeletado.setId(4L);
		
		permissaoRepository.deleteById(permissaoDeletado.getId());
		
		// Listar todas as permissões do banco de dados
		List<Permissao> permissaos = permissaoRepository.findAll();
		permissaos.forEach(permissao -> {
			System.out.println("ID: " + permissao.getId() + " - Nome: " + permissao.getNome() + 
					" - Descrição: " + permissao.getDescricao());
		});
	}
}
