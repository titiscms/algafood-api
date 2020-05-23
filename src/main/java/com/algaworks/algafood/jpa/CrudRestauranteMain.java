package com.algaworks.algafood.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.repository.CozinhaRepository;
import com.algaworks.algafood.domain.model.repository.RestauranteRepository;

public class CrudRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

		// Listar todos os restaurantes do banco de dados inicial
		List<Restaurante> restaurantesAntes = restauranteRepository.findAll();
		restaurantesAntes.forEach(restaurante -> {
			System.out.println("ID: " + restaurante.getId() + " - Nome: " + restaurante.getNome() + 
					" - Taxa Frete: " + restaurante.getTaxaFrete() + " - Nome da cozinha: " + restaurante.getCozinha().getNome());
		});
		
		// Salvando 1 restaurante
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("Coco Bambu");
		restaurante1.setTaxaFrete(new BigDecimal(15.5));
		Cozinha cozinha1 = cozinhaRepository.getOne(2L);
		restaurante1.setCozinha(cozinha1);
		
		restauranteRepository.save(restaurante1);
		
		// Listar todos os restaurantes do banco de dados depois da inclusão
		List<Restaurante> restaurantesDepois = restauranteRepository.findAll();
		restaurantesDepois.forEach(restaurante -> {
			System.out.println("ID: " + restaurante.getId() + " - Nome: " + restaurante.getNome() + 
					" - Taxa Frete: " + restaurante.getTaxaFrete() + " - Nome da cozinha: " + restaurante.getCozinha().getNome());
		});
		
		// Buscar restaurante com ID = 2
		Restaurante restauranteBuscado= restauranteRepository.getOne(2L);
		System.out.println("ID: " + restauranteBuscado.getId() + " - Nome: " + restauranteBuscado.getNome() + 
				" - Taxa Frete: " + restauranteBuscado.getTaxaFrete() + " - Nome da cozinha: " + restauranteBuscado.getCozinha().getNome());
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setId(restauranteBuscado.getId());
		restaurante2.setNome("Piero");
		restaurante2.setTaxaFrete(new BigDecimal(20));
		Cozinha cozinha2 = cozinhaRepository.getOne(restauranteBuscado.getCozinha().getId());
		restaurante2.setCozinha(cozinha2);
		
		// atualizar restaurante com ID = 2
		Restaurante restauranteAlterado = restauranteRepository.save(restaurante2);
		System.out.println("ID: " + restauranteAlterado.getId() + " - Nome: " + restauranteAlterado.getNome() + 
				" - Taxa Frete: " + restauranteAlterado.getTaxaFrete() + " - Nome da cozinha: " + restauranteAlterado.getCozinha().getNome());

		// Excluir restaurante com ID = 1
		Restaurante restauranteDeletado = new Restaurante();
		restauranteDeletado.setId(1L);
		
		restauranteRepository.deleteById(restauranteDeletado.getId());
		
		// Listar todos os restaurantes do banco de dados
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		restaurantes.forEach(restaurante -> {
			System.out.println("ID: " + restaurante.getId() + " - Nome: " + restaurante.getNome() + 
					" - Taxa Frete: " + restaurante.getTaxaFrete() + " - Nome da cozinha: " + restaurante.getCozinha().getNome());
		});
	}
}
