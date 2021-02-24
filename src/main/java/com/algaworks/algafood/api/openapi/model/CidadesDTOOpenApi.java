package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CidadeDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CidadesDTO")
@Setter
@Getter
public class CidadesDTOOpenApi {

	private CidadesEmbeddedDTOOpenApi _embedded;
	
	private Links _links;
	
	
	@ApiModel("CidadesEmbeddedDTO")
	@Data
	private class CidadesEmbeddedDTOOpenApi {
	
		private List<CidadeDTO> cidades;
	}
	
}
