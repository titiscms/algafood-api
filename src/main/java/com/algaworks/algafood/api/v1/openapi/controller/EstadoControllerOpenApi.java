package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.input.EstadoDTOInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista todos os estados")
	public CollectionModel<EstadoDTO> listar();
	
	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID de estado inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoDTO buscar(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long id);
	
	@ApiOperation("Cadastra um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Estado cadastrado")
	})
	EstadoDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um estado novo", required = true)
			EstadoDTOInput estadoDTOInput);
	
	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Estado atualizado"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoDTO atualizar(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long id,
			@ApiParam(name = "corpo", value = "Representação de um estado com novos dados")
			EstadoDTOInput estadoDTOInput);
	
	@ApiOperation("Excluí um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Estado excluído"),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de um estado", example = "1", required = true)
			Long id);
}
