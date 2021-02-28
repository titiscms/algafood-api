package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTOInput {
	
	@ApiModelProperty(example = "Thai Food", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "34.90", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaDTOInputId cozinha;
	
	@Valid
	@NotNull
	private EnderecoDTOInput endereco;
}
