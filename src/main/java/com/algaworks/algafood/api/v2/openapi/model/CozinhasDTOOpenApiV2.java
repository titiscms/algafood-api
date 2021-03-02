package com.algaworks.algafood.api.v2.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v2.model.CozinhaDTOV2;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasDTO")
@Setter
@Getter
public class CozinhasDTOOpenApiV2 {

	private CozinhasEmbeddedDTOOpenApiV2 _embedded;
	private Links _links;
	private PageModelOpenApiV2 page;
	
	@ApiModel("CozinhasEmbeddedDTO")
	@Data
	private class CozinhasEmbeddedDTOOpenApiV2 {
		
		private List<CozinhaDTOV2> cozinhas;
	}
	
}
