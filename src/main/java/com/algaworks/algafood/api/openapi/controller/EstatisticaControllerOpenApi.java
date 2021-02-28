package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.controller.EstatisticaController.EstatisticasDTO;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estatísticas")
public interface EstatisticaControllerOpenApi {

	@ApiOperation("Consulta estatísticas de vendas diárias")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1"),
		@ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido", example = "2020-08-16T00:00:01Z"),
		@ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido", example = "2020-08-17T23:59:59Z")
	})
	@ApiResponses({
		@ApiResponse(code = 400, message = "", response = Problem.class)
	})
	public List<VendaDiaria> consultarVendasDiarias(
			VendaDiariaFilter filtro, 
			@ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", example = "+00:00")
			String timeOffset);
	
	public ResponseEntity<byte[]> consultarVendasDiariasPDF(
			VendaDiariaFilter filtro,
			String timeOffset);
	
	@ApiOperation(value = "Estatísticas", hidden = true)
	EstatisticasDTO estatisticas();
	
}
