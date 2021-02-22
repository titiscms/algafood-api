package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FotoProdutoDTOAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
	}
	
	@Override
	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
//		FotoProdutoDTO fotoProdutoDTO = createModelWithId(fotoProduto.getId(), fotoProduto);
//		
//		modelMapper.map(fotoProduto, fotoProdutoDTO);
		
		FotoProdutoDTO fotoProdutoDTO = modelMapper.map(fotoProduto, FotoProdutoDTO.class);
		
		fotoProdutoDTO.add(algaLinks.linkToFotoProduto(fotoProduto.getRestauranteId(), fotoProduto.getId()));
		
		fotoProdutoDTO.add(algaLinks.linkToProduto(fotoProduto.getRestauranteId(), fotoProduto.getId(), "produto"));
		
		return fotoProdutoDTO;
	}
}
