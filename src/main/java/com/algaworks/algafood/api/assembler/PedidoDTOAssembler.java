package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
		
		modelMapper.map(pedido, pedidoDTO);
		
		pedidoDTO.add(linkTo(PedidoController.class).withRel("pedidos"));
		
		pedidoDTO.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
				.buscar(pedido.getRestaurante().getId())).withSelfRel());
		
		pedidoDTO.getCliente().add(linkTo(methodOn(UsuarioController.class)
				.buscar(pedido.getCliente().getId())).withSelfRel());
				
		pedidoDTO.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
				.buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());
		
		pedidoDTO.getEnderecoEntrega().add(linkTo(methodOn(CidadeController.class)
				.buscar(pedido.getEnderecoEntrega().getCidade().getId())).withRel("cidade"));
		
		pedidoDTO.getEnderecoEntrega().add(linkTo(methodOn(EstadoController.class)
				.buscar(pedido.getEnderecoEntrega().getCidade().getEstado().getId())).withRel("estado"));
		
		pedidoDTO.getItens().forEach(item -> {
			item.add(linkTo(methodOn(RestauranteProdutoController.class)
					.buscar(pedido.getRestaurante().getId(), item.getProdutoId()))
					.withRel("produto"));
		});
		
		return pedidoDTO; 
	}
	
	public List<PedidoDTO> toListPedidoDTO(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
