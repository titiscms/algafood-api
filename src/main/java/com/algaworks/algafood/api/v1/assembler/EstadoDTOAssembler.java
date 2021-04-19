package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public EstadoDTOAssembler() {
		super(EstadoController.class, EstadoDTO.class);
	}
	
	@Override
	public EstadoDTO toModel(Estado estado) {
		EstadoDTO estadoDTO = createModelWithId(estado.getId(), estado);
		
		modelMapper.map(estado, estadoDTO);
		
		if (algaSecurity.podeConsultarEstados()) {
			estadoDTO.add(algaLinks.linkToEstados("estados"));
		}
		
		return estadoDTO;
	}
	
	@Override
	public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> estados) {
		CollectionModel<EstadoDTO> estadosDTO = super.toCollectionModel(estados);
		
		if (algaSecurity.podeConsultarEstados()) {
			estadosDTO.add(algaLinks.linkToEstados());
		}
		
		return estadosDTO; 
	}
	
}
