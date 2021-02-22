package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation("Lista os grupos associados a um usuário")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID de usuário inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class),
	})
	public CollectionModel<GrupoDTO> listar(
			@ApiParam(value = "ID de usuário", example = "1", required = true)
			Long usuarioId);
	
	@ApiOperation("Associa grupo com usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class),
	})
	public void associarGrupo(
			@ApiParam(value = "ID de usuário", example = "1", required = true)
			Long usuarioId,
			@ApiParam(value = "ID de grupo", example = "2", required = true)
			Long grupoId);

	@ApiOperation("Desassocia grupo com usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class),
	})
	public void desassociarGrupo(
			@ApiParam(value = "ID de usuário", example = "1", required = true)
			Long usuarioId,
			@ApiParam(value = "ID de grupo", example = "2", required = true)
			Long grupoId);
}
