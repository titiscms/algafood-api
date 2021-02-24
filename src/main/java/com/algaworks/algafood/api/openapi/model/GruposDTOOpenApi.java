package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.GrupoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("GruposDTO")
@Setter
@Getter
public class GruposDTOOpenApi {

	private GrupoEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("GrupoEmbeddedDTO")
	@Data
	private class GrupoEmbeddedDTOOpenApi {
		
		private List<GrupoDTO> grupos;
	}
	
}
