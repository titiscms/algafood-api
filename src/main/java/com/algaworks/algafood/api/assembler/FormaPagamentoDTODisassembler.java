package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoDTOInput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTODisassembler {
	
	@Autowired
	public ModelMapper modelMapper;

	public FormaPagamento toDomainObject(FormaPagamentoDTOInput formaPagamentoDTOInput) {
		return modelMapper.map(formaPagamentoDTOInput, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoDTOInput formaPagamentoDTOInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoDTOInput, formaPagamento);
	}
}
