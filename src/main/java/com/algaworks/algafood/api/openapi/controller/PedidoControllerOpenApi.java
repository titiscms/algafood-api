package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoDTOInput;
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
	
	@ApiImplicitParams({
		@ApiImplicitParam(
				value = "Nome das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", type = "string")
	})
	@ApiOperation("Pesquisa os pedidos")
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro,	Pageable pageable);
	
	@ApiImplicitParams({
		@ApiImplicitParam(
				value = "Nome das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos", paramType = "query", type = "string")
	})
	@ApiResponses({
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	@ApiOperation("Busca um pedido pelo código")
	public PedidoDTO buscar(
			@ApiParam(value = "Código de um pedido", example = "ad759d44-3af1-4fcc-a022-52bdb374a23c", required = true)
			String codigoPedido);
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Pedido registrado"),
	})
	@ApiOperation("Registra um pedido")
	public PedidoDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true)
			PedidoDTOInput pedidoDTOInput);
}
