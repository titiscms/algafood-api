package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Setter
@Getter
public class RestauranteBasicoDTO extends RepresentationModel<RestauranteBasicoDTO> {
	
	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Food")
	private String nome;
	
	@ApiModelProperty(example = "34.90")
	private BigDecimal taxaFrete;
	
	private CozinhaDTO cozinha;
	
}
