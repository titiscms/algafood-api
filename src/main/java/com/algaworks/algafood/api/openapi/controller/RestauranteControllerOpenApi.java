package com.algaworks.algafood.api.openapi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeDTO;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista os restaurantes")
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção dos restaurantes", allowableValues = "apenas-nome",
				name = "projecao", paramType = "query", type = "string")
	})
//	@JsonView(RestauranteView.Resumo.class)
	CollectionModel<RestauranteBasicoDTO> listar();
	
	@ApiIgnore
	@ApiOperation(value = "Lista os restaurantes", hidden = true)
//	@JsonView(RestauranteView.ApenasNome.class)
	CollectionModel<RestauranteApenasNomeDTO> listarApenasNome();
	
	@ApiOperation(value = "Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteDTO buscar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long id);
	
	@ApiOperation(value = "Cadastra um restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante cadastrado")
	})
	RestauranteDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um restaurante", required = true)
			RestauranteDTOInput restauranteDTOInput);
	
	@ApiOperation(value = "Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Restaurante atualizado"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteDTO atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long id,
			@ApiParam(name = "corpo", value = "Representação de um restaurante como os novos dados", example = "1")
			RestauranteDTOInput restauranteDTOInput);
	
	@ApiIgnore
	RestauranteDTO atualizarParcial(Long id, Map<String, Object> campos, HttpServletRequest request);
	
	@ApiOperation(value = "Exclui um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante excluído"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void remover(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long id);
	
	@ApiOperation(value = "Ativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> ativar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long id);
	
	@ApiOperation(value = "Inativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> inativar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long id);
	
	@ApiOperation(value = "Ativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	ResponseEntity<Void> ativar(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", example = "[ 1, 2, 4 ]", required = true)
			List<Long> ids);
	
	@ApiOperation(value = "Inativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes inativados com sucesso")
	})
	ResponseEntity<Void> inativar(
			@ApiParam(name = "corpo", value = "IDs de restaurantes", example = "[ 1, 2, 4 ]", required = true)
			List<Long> ids);
	
	@ApiOperation(value = "Abre um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> abrir(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long id);
	
	@ApiOperation(value = "Fecha um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> fechar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true)
			Long id);
}
