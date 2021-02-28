package com.algaworks.algafood.api.v1.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.model.input.PedidoDTOInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
	
	@ApiOperation("Pesquisa os pedidos")
	@ApiImplicitParams({
		@ApiImplicitParam(
				value = "Nome das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", type = "string")
	})
	PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);
	
	@ApiOperation("Busca um pedido pelo código")
	@ApiImplicitParams({
		@ApiImplicitParam(
				value = "Nome das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", type = "string")
	})
	@ApiResponses({
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	PedidoDTO buscar(
			@ApiParam(value = "Código de um pedido", example = "ad759d44-3af1-4fcc-a022-52bdb374a23c", required = true)
			String codigoPedido);
	
	@ApiOperation("Registra um pedido")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Pedido registrado")
	})
	PedidoDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
			PedidoDTOInput pedidoDTOInput);
}
