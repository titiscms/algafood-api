package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoDTOInput {

	@ApiModelProperty(example = "Gerente", required = true)
	@NotBlank
	public String nome;
}
