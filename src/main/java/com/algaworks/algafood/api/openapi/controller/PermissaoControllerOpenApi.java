package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.openapi.model.PermissoesDTOOpenApi;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

	@ApiOperation(value = "Listar todas as permissões", response = PermissoesDTOOpenApi.class)
	public CollectionModel<PermissaoDTO> listar();
	
}
