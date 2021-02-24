package com.algaworks.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.FormaPagamentoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel("FormasPagamentoDTO")
@Setter
@Getter
public class FormasPagamentoDTOOpenApi {
	
	private FormaPagamentoEmbeddedDTOOpenApi _embedded;
	private Links _links;
	
	@ApiModel("FormaPagamentoEmbeddedDTO")
	@Data
	private class FormaPagamentoEmbeddedDTOOpenApi {
		
		private List<FormaPagamentoDTO> formasPagamento;
	}

}
