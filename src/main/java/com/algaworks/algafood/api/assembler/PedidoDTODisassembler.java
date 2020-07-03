package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.PedidoDTOInput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoDTOInput pedidoDTOInput) {
		return modelMapper.map(pedidoDTOInput, Pedido.class);
	}
}
