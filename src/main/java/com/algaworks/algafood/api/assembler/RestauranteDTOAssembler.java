package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteDTO toRestauranteDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toListRestauranteDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toRestauranteDTO(restaurante))
				.collect(Collectors.toList());
	}
	
	public RestauranteDTOInput toRestauranteDTOInput(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTOInput.class);
	}
}
