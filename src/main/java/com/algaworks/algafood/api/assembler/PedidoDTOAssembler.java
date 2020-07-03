package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoDTO toPedidoDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> toListPedidoDTO(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toPedidoDTO(pedido))
				.collect(Collectors.toList());
	}
}
