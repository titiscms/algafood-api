package com.algaworks.algafood.api.v1.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTOInputNomeEmail {

	@ApiModelProperty(example = "Thiago", required = true)
	@NotBlank
	private String nome;
	
	@ApiModelProperty(example = "thiago@algafood.com", required = true)
	@Email
	@NotBlank
	private String email;
}