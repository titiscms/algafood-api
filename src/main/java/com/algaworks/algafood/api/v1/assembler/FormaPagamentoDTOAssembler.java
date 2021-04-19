package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler 
	extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public FormaPagamentoDTOAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoDTO.class);
	}
	
	@Override
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		FormaPagamentoDTO formaPagamentoDTO = createModelWithId(formaPagamento.getId(), formaPagamento);
		
		modelMapper.map(formaPagamento, formaPagamentoDTO);
		
		if (algaSecurity.podeConsultarFormasPagamento()) {
			formaPagamentoDTO.add(algaLinks.linkToFormasPagamento("formasPagamento"));
		}
		
		return formaPagamentoDTO;
	}
	
	@Override
	public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> formasPagamento) {
		CollectionModel<FormaPagamentoDTO> formasPagamentoDTO = super.toCollectionModel(formasPagamento);
		
		if (algaSecurity.podeConsultarFormasPagamento()) {
			formasPagamentoDTO.add(algaLinks.linkToFormasPagamento());
		}
		
		return formasPagamentoDTO; 
	}
	
}
