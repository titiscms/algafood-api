package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoDTO extends RepresentationModel<PedidoDTO> {
	
	@ApiModelProperty(example = "ad759d44-3af1-4fcc-a022-52bdb374a23c")
	private String codigo;
	
	@ApiModelProperty(example = "298.90")
	private BigDecimal subtotal;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "308.90")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "CRIADO")
	private String status;
	
	@ApiModelProperty(example = "2020-08-06T02:21:04Z")
	private OffsetDateTime dataCriacao;
	
	@ApiModelProperty(example = "2020-08-06T02:21:04Z")
	private OffsetDateTime dataConfirmacao;
	
	@ApiModelProperty(example = "2020-08-06T02:21:04Z")
	private OffsetDateTime dataEntrega;
	
	@ApiModelProperty(example = "2020-08-06T02:21:04Z")
	private OffsetDateTime dataCancelamento;
	
	private RestauranteResumoDTO restaurante;
	
	private UsuarioDTO cliente;
	
	private FormaPagamentoDTO formaPagamento;
	
	private EnderecoDTO enderecoEntrega;
	
	private List<ItemPedidoDTO> itens;
}
