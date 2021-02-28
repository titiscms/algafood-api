package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Links")
@Setter
@Getter
public class LinksModelOpenApi {
	
	private LinkDTO rel;
	
	@ApiModel("Link")
	@Setter
	@Getter
	private class LinkDTO {
		
		private String href;
		private boolean templated;
	}

}
