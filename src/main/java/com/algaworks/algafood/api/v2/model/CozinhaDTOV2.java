package com.algaworks.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhaDTO")
@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaDTOV2 extends RepresentationModel<CozinhaDTOV2> {

	@ApiModelProperty(example = "1")
	private Long cozinhaId;
	
	@ApiModelProperty(example = "Tailandesa")
	private String cozinhaNome;
	
}
