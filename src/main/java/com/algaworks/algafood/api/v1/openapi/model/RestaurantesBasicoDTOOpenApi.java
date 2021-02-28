package com.algaworks.algafood.api.v1.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestaurantesBasicoDTO")
@Setter
@Getter
public class RestaurantesBasicoDTOOpenApi {

	private RestaurantesEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("RestaurantesEmbeddedDTO")
	@Data
	private class RestaurantesEmbeddedDTOOpenApi {
		
		private List<RestauranteBasicoDTO> restaurantes;
	}
	
}
