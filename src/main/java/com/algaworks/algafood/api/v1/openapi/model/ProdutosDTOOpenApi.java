package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.ProdutoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("ProdutosDTO")
@Setter
@Getter
public class ProdutosDTOOpenApi {

	private ProdutosEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("ProdutosEmbeddedDTO")
	@Data
	private class ProdutosEmbeddedDTOOpenApi {
		
		private List<ProdutoDTO> produtos;
	}
	
}
