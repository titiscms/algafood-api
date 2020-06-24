package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTOInputSenha {

	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha;
}