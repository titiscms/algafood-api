package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public GrupoDTOAssembler() {
		super(GrupoController.class, GrupoDTO.class);
	}
	
	@Override
	public GrupoDTO toModel(Grupo grupo) {
		GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
		
		modelMapper.map(grupo, grupoDTO);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {

			grupoDTO.add(algaLinks.linkToGrupos("grupos"));
			
			grupoDTO.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
			
		}
		
		return grupoDTO;
	}
	
	@Override
	public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> grupos) {
		CollectionModel<GrupoDTO> gruposDTO = super.toCollectionModel(grupos);
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			gruposDTO.add(algaLinks.linkToGrupos());
		}
		
		return gruposDTO; 
	}
	
}
