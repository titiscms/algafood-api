package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoDTOAssembler 
	extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public RestauranteBasicoDTOAssembler() {
		super(RestauranteController.class, RestauranteBasicoDTO.class);
	}
	
	@Override
	public RestauranteBasicoDTO toModel(Restaurante restaurante) {
		RestauranteBasicoDTO restauranteBasicoDTO = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteBasicoDTO);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteBasicoDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteBasicoDTO.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}
		
		return restauranteBasicoDTO;
	}
	
	@Override
	public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		CollectionModel<RestauranteBasicoDTO> restaurantesDTO = super.toCollectionModel(restaurantes);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restaurantesDTO.add(algaLinks.linkToRestaurantes());
		}
		
		return restaurantesDTO;
	}
	
}
