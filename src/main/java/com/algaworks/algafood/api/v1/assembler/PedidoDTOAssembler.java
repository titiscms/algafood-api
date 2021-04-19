package com.algaworks.algafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
		
		modelMapper.map(pedido, pedidoDTO);

	    /*
	     * Não usei o método algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
	     * porque na geração do link, não temos o id do cliente e do restaurante,
	     * então precisamos saber apenas se a requisição está autenticada e tem o escopo de leitura
	     */
	    if (algaSecurity.podePesquisarPedidos()) {
	    	pedidoDTO.add(algaLinks.linkToPedidos("pedidos"));
	    }
		
		if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
			
			if (pedido.podeSerConfirmado()) {
				pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}
			
			if (pedido.podeSerCancelado()) {
				pedidoDTO.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}
			
			if (pedido.podeSerEntregue()) {
				pedidoDTO.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}
			
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
		}
		
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoDTO.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
		}
		
		if (algaSecurity.podeConsultarFormasPagamento()) {
			pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
		}
		
		if (algaSecurity.podeConsultarCidades()) {
			pedidoDTO.getEnderecoEntrega().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId(), "cidade"));
		}
		
		if (algaSecurity.podeConsultarEstados()) {
			pedidoDTO.getEnderecoEntrega().add(algaLinks.linkToEstado(pedido.getEnderecoEntrega().getCidade().getEstado().getId(), "estado"));
		}

		/*
		 * Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
		 */
	    if (algaSecurity.podeConsultarRestaurantes()) {
	    	
	    	pedidoDTO.getItens().forEach(item -> {
	    		item.add(algaLinks.linkToProduto(pedido.getRestaurante().getId(), item.getProdutoId(), "produto"));
	    	});
	    	
	    }
		
		return pedidoDTO; 
	}
	
	public List<PedidoDTO> toListPedidoDTO(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
