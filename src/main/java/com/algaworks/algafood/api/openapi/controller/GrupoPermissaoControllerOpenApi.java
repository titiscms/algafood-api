package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PermissaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation("Listar as permissões associadas a um grupo")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
	public CollectionModel<PermissaoDTO> listar(
			@ApiParam(value = "ID do grupo", example = "1", required = true)
			Long grupoId);
	
	@ApiOperation("Associação de permissão com grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Permissão associada com sucesso"),
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrado", response = Problem.class)
	})
	public ResponseEntity<Void> associar(
			@ApiParam(value = "ID do grupo", example = "1", required = true)
			Long grupoId,
			@ApiParam(value = "ID da permissão", example = "2", required = true)
			Long permissaoId);
	
	@ApiOperation("Desassociação de permissão com grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Permissão desassociada com sucesso"),
		@ApiResponse(code = 404, message = "Grupo ou permissão não encontrado", response = Problem.class)
	})
	public ResponseEntity<Void> desassociar(
			@ApiParam(value = "ID do grupo", example = "1", required = true)
			Long grupoId,
			@ApiParam(value = "ID da permissão", example = "2", required = true)
			Long permissaoId);
}
