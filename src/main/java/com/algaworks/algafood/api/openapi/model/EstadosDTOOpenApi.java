package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.EstadoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("EstadosDTO")
@Setter
@Getter
public class EstadosDTOOpenApi {

	private EstadoEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("EstadoEmbeddedDTO")
	@Data
	private class EstadoEmbeddedDTOOpenApi {
		
		private List<EstadoDTO> estados;
	}
	
}
