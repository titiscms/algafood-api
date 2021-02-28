package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.EstadoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("EstadosDTO")
@Setter
@Getter
public class EstadosDTOOpenApi {

	private EstadosEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("EstadosEmbeddedDTO")
	@Data
	private class EstadosEmbeddedDTOOpenApi {
		
		private List<EstadoDTO> estados;
	}
	
}
