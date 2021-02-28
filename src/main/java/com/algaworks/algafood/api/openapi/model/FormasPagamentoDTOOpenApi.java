package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("FormasPagamentoDTO")
@Setter
@Getter
public class FormasPagamentoDTOOpenApi {
	
	private FormasPagamentoEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("FormasPagamentoEmbeddedDTO")
	@Data
	private class FormasPagamentoEmbeddedDTOOpenApi {
		
		private List<FormaPagamentoDTO> formasPagamento;
	}

}
