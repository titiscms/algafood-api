package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CozinhasDTO")
@Setter
@Getter
public class CozinhasDTOOpenApi {

	private CozinhasEmbeddedDTOOpenApi _embedded;
	private Links _links;
	private PageModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedDTO")
	@Data
	private class CozinhasEmbeddedDTOOpenApi {
		
		private List<CozinhaDTO> cozinhas;
	}
	
}
