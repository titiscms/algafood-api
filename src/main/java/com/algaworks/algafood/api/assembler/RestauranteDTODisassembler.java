package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTODisassembler {
	
	public Restaurante toDomainObject(RestauranteDTOInput restauranteDTOInput) {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteDTOInput.getCozinha().getId());
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteDTOInput.getNome());
		restaurante.setTaxaFrete(restauranteDTOInput.getTaxaFrete());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
}
