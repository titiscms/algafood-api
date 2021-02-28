package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.model.input.UsuarioDTOInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioDTOInputNomeEmail;
import com.algaworks.algafood.api.v1.model.input.UsuarioDTOInputSenha;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista todos os usuários")
	public CollectionModel<UsuarioDTO> listar();
	
	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID de usuário inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	public UsuarioDTO buscar(
			@ApiParam(value = "ID de usuário", example = "1", required = true)
			Long id);
	
	@ApiOperation("Adiciona um usuário")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário criado")
	})
	public UsuarioDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um usuário novo", required = true)
			UsuarioDTOInput usuarioDTOInput);
	
	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Usuário atualizado"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	public UsuarioDTO atualizar(
			@ApiParam(value = "ID de usuário", example = "1", required = true)
			Long id,
			@ApiParam(name = "corpo", value = "Representação de um usuário com novos dados", required = true)
			UsuarioDTOInputNomeEmail usuarioDTOInputNomeEmail);
	
	@ApiOperation("Exclui um usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Usuário excluído"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	public void remover(
			@ApiParam(value = "ID de usuário", example = "1", required = true)
			Long id);
	
	@ApiOperation("Atualiza a senha de usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Senha de usuario atualizada com sucesso"),
		@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	})
	public void atualizarSenha(
			@ApiParam(value = "ID de usuário", example = "1", required = true)
			Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma senha nova", required = true)
			UsuarioDTOInputSenha usuarioDTOInputSenha);
}
