package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
		
		modelMapper.map(pedido, pedidoDTO);

		pedidoDTO.add(algaLinks.linkToPedidos("pedidos"));
		
		pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		
		pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
				
		pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		
		pedidoDTO.getEnderecoEntrega().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId(), "cidade"));
		
		pedidoDTO.getEnderecoEntrega().add(algaLinks.linkToEstado(pedido.getEnderecoEntrega().getCidade().getEstado().getId(), "estado"));
		
		pedidoDTO.getItens().forEach(item -> {
			item.add(algaLinks.linkToProduto(pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
		});
		
		return pedidoDTO; 
	}
	
	public List<PedidoDTO> toListPedidoDTO(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
