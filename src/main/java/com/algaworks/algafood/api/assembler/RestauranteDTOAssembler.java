package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler
	extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}
	
	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteDTO);
		
		restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		restauranteDTO.getEndereco().add(algaLinks.linkToCidade(
				restaurante.getEndereco().getCidade().getId()).withRel("cidade"));
		
		restauranteDTO.getEndereco().add(algaLinks.linkToEstado(
				restaurante.getEndereco().getCidade().getEstado().getId()).withRel("estado"));
        
		restauranteDTO.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
        
		restauranteDTO.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
		
		
		return restauranteDTO;
	}
	
	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		return super.toCollectionModel(restaurantes).add(algaLinks.linkToRestaurantes("restuarantes"));
	}

	public RestauranteDTOInput toRestauranteDTOInput(Restaurante restauranteAtual) {
		return modelMapper.map(restauranteAtual, RestauranteDTOInput.class);
	}
	
}
