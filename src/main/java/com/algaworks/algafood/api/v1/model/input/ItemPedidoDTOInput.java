package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemPedidoDTOInput {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "2", required = true)
	@NotNull
    @PositiveOrZero
	private Integer quantidade;
	
	@ApiModelProperty(example = "Sem pimenta")
	private String observacao;
}
