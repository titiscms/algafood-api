package com.algaworks.algafood.api.v2.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.input.CidadeDTOInputV2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {

	@ApiOperation("Lista as cidades")
	CollectionModel<CidadeDTOV2> listar();
	
	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeDTOV2 buscar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true)
			Long id);		
	
	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	CidadeDTOV2 adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true)
			CidadeDTOInputV2 cidadeDTOInput);
	
	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeDTOV2 atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
			Long id,
			@ApiParam(name = "corpo", value = "Representação de uma cidade como os novos dados", example = "1") 
			CidadeDTOInputV2 cidadeDTOInput);
	
	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluída"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) 
			Long id); 
}