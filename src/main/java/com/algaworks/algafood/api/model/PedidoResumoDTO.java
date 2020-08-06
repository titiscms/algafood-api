package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoResumoDTO {
	
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
	
	private RestauranteResumoDTO restaurante;
	
	@ApiModelProperty(example = "Thiago")
	private String nomeCliente;
}
