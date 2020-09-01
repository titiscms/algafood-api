package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTO extends RepresentationModel<EnderecoDTO> {

	@ApiModelProperty(example = "03221-001")
	private String cep;
	
	@ApiModelProperty(example = "Rua Padre coelho")
	private String logradouro;
	
	@ApiModelProperty(example = "134")
	private String numero;
	
	@ApiModelProperty(example = "apto 115")
	private String complemento;
	
	@ApiModelProperty(example = "Bairro das Laranjeiras")
	private String bairro;
	
	@ApiModelProperty(example = "Fortaleza")
	private String cidade;
	
	@ApiModelProperty(example = "Ceará")
	private String estado;
}
