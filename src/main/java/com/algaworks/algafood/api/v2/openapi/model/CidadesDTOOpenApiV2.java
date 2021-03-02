package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v2.model.CidadeDTOV2;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadesDTO")
@Setter
@Getter
public class CidadesDTOOpenApiV2 {

	private CidadesEmbeddedDTOOpenApiV2 _embedded;
	
	private Links _links;
	
	
	@ApiModel("CidadesEmbeddedDTO")
	@Data
	private class CidadesEmbeddedDTOOpenApiV2 {
	
		private List<CidadeDTOV2> cidades;
	}
	
}
