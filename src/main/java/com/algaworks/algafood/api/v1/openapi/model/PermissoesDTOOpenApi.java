package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.PermissaoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("PermissoesDTO")
@Setter
@Getter
public class PermissoesDTOOpenApi {

	private PermissoesEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("PermissoesEmbeddedDTO")
	@Data
	private class PermissoesEmbeddedDTOOpenApi {
		
		private List<PermissaoDTO> permissoes;
	}
	
}
