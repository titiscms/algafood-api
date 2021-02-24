package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.UsuarioDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("UsuariosDTO")
@Setter
@Getter
public class UsuariosDTOOpenApi {

	private UsuariosEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("UsuariosEmbeddedDTO")
	@Data
	private class UsuariosEmbeddedDTOOpenApi {
		
		private List<UsuarioDTO> usuarios;
	}
	
}
