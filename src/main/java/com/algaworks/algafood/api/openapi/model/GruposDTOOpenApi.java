package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.GrupoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("GruposDTO")
@Setter
@Getter
public class GruposDTOOpenApi {

	private GruposEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("GruposEmbeddedDTO")
	@Data
	private class GruposEmbeddedDTOOpenApi {
		
		private List<GrupoDTO> grupos;
	}
	
}
