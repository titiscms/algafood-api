package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTOInputSenha {

	@ApiModelProperty(example = "password", required = true)
	@NotBlank
	private String senhaAtual;
	
	@ApiModelProperty(example = "newpassword", required = true)
	@NotBlank
	private String novaSenha;
}