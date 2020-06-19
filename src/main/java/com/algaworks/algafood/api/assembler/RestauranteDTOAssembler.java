package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.CozinhaDTOInputId;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {
	
	public RestauranteDTO toRestauranteDTO(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinha(cozinhaDTO);
		
		return restauranteDTO;
	}
	
	public List<RestauranteDTO> toListRestauranteDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toRestauranteDTO(restaurante))
				.collect(Collectors.toList());
	}
	
	public RestauranteDTOInput toRestauranteDTOInput(Restaurante restaurante) {
		CozinhaDTOInputId cozinhaDTOInputId = new CozinhaDTOInputId();
		cozinhaDTOInputId.setId(restaurante.getCozinha().getId());
		
		RestauranteDTOInput restauranteDTOInput = new RestauranteDTOInput();
		restauranteDTOInput.setNome(restaurante.getNome());
		restauranteDTOInput.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTOInput.setCozinha(cozinhaDTOInputId);
		
		return restauranteDTOInput;
	}
}
