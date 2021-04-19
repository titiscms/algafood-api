package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.model.FotoProdutoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public FotoProdutoDTOAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
	}
	
	@Override
	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
		FotoProdutoDTO fotoProdutoDTO = modelMapper.map(fotoProduto, FotoProdutoDTO.class);
		
		/*
		 * Quem pode consultar restaurantes, também pode consultar os produtos e fotos
		 */
	    if (algaSecurity.podeConsultarRestaurantes()) {
	    	
	    	fotoProdutoDTO.add(algaLinks.linkToFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getId()));

	    	fotoProdutoDTO.add(algaLinks.linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getId(), "produto"));
	    	
	    }
		
		return fotoProdutoDTO;
	}
	
}
