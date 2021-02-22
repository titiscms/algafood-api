package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoDTOAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PermissaoDTOAssembler() {
		super(GrupoPermissaoController.class, PermissaoDTO.class);
	}
	
	@Override
	public PermissaoDTO toModel(Permissao permissao) {
		PermissaoDTO permissaoDTO = modelMapper.map(permissao, PermissaoDTO.class);
		
		return permissaoDTO;
	}
	
	@Override
	public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> permissões) {
		return super.toCollectionModel(permissões).add(algaLinks.linkToPermissoes());
	}
	
}
