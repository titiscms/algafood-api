package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoDTOInput;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoDTOOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "FormasPagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Lista todas as formas de pagamento", response = FormasPagamentoDTOOpenApi.class)
	ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);
	
	@ApiOperation("Busca forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	ResponseEntity<FormaPagamentoDTO> buscar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
			Long id, ServletWebRequest request);
	
	@ApiOperation("Cadastra forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Forma de pagamento cadastrada")
	})
	FormaPagamentoDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento", required = true)
			FormaPagamentoDTOInput formaPagamentoDTOInput);
	
	@ApiOperation("Atualiza forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	FormaPagamentoDTO atualizar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
			Long id,
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com novos dados", example = "1")
			FormaPagamentoDTOInput formaPagamentoDTOInput);
	
	@ApiOperation("Exclui forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Forma de pagamento excluída" ),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true)
			Long id);
}
