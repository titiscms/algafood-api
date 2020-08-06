package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTOInput {

	@ApiModelProperty(example = "03221-001", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Rua Padre coelho", required = true)
	@NotBlank
	private String logradouro;
	
	@ApiModelProperty(example = "134", required = true)
	@NotBlank
	private String numero;
	
	@ApiModelProperty(example = "apto 115")
	private String complemento;
	
	@ApiModelProperty(example = "Bairro das Laranjeiras", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeDTOInputId cidade;
}
