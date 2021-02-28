package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.api.v1.model.GrupoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	CollectionModel<GrupoDTO> listar();
	
	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID de grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoDTO buscar(
			@ApiParam(value = "ID de um grupo" , example = "1", required = true)
			Long id);
	
	@ApiOperation("Cadastra um grupo")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Grupo cadastrado")
	})
	GrupoDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true)
			GrupoDTOInput grupoDTOInput);
	
	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Grupo atualizado"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoDTO atualizar(
			@ApiParam(value = "ID de um grupo" , example = "1", required = true)
			Long id,
			@ApiParam(name = "corpo", value = "Representação de um grupo como os novos dados", example = "1") 
			GrupoDTOInput grupoDTOInput);

	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo excluído"),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de um grupo" , example = "1", required = true)
			Long id);
}