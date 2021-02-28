package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiOperation("Confirmação de um pedido por código")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	void confirmar(
			@ApiParam(value = "Código de um pedido", example = "ad759d44-3af1-4fcc-a022-52bdb374a23c", required = true)
			String codigoPedido);
	
	@ApiOperation("Cancelamento de um pedido por código")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	void cancelar(
			@ApiParam(value = "Código de um pedido", example = "ad759d44-3af1-4fcc-a022-52bdb374a23c", required = true)
			String codigoPedido);
	
	@ApiOperation("Registro de entrega de um pedido por código")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido entregue com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	void entregar(
			@ApiParam(value = "Código de um pedido", example = "ad759d44-3af1-4fcc-a022-52bdb374a23c", required = true)
			String codigoPedido);
}
