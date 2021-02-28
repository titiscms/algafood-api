package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Setter
@Getter
public class CidadeDTOV2 extends RepresentationModel<CidadeDTOV2> {

	@ApiModelProperty(example = "1")
	private Long cidadeId;
	
	@ApiModelProperty(example = "Uberlandia")
	private String cidadeNome;
	
	@ApiModelProperty(example = "1")
	private Long estadoId;
	
	@ApiModelProperty(example = "Minas Gerais")
	private String estadoNome;
	
}
