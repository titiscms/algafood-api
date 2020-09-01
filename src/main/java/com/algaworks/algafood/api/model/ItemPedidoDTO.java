package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoDTO extends RepresentationModel<ItemPedidoDTO> {

	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Frango xadrez com molho soyo")
	private String produtoNome;
	
	@ApiModelProperty(example = "2")
	private Integer quantidade;
	
	@ApiModelProperty(example = "45.90")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "91.80")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Sem pimenta")
	private String observacao;
}
