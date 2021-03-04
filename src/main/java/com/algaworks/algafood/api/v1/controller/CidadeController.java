package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.CidadeDTODisassembler;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeDTOInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
//Configuração para versionamento da api por MediaType
//@RequestMapping(path = "/cidades", produces = AlgafoodMediaTypes.V1_APPLICATION_JSON_VALUE)
//Configuração para versionamento da api por URI
@RequestMapping(path = "/v1/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeDTODisassembler cidadeDTODisassembler;
	
//	@Deprecated - método fica depreciado na documentação
	@Override
	@GetMapping
	public CollectionModel<CidadeDTO> listar() {
		List<Cidade> cidades = cidadeRepository.findAll();
		
		return cidadeDTOAssembler.toCollectionModel(cidades);
	}
	
//	@Deprecated - método fica depreciado na documentação
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable(value = "cidadeId") Long id) {
		Cidade cidade = cadastroCidade.findOrFail(id);
		
		return cidadeDTOAssembler.toModel(cidade);
	}
	
//	@Deprecated - método fica depreciado na documentação
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		try {
			Cidade cidade = cidadeDTODisassembler.toDomainObject(cidadeDTOInput);
			
			CidadeDTO cidadeDTO = cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidade));
					
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
//	@Deprecated - método fica depreciado na documentação
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable(value = "cidadeId") Long id,
			@RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		
		try {
			Cidade cidadeAtual = cadastroCidade.findOrFail(id);
			
			cidadeDTODisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);
			
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
//	@Deprecated - método fica depreciado na documentação
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "cidadeId") Long id) {
		cadastroCidade.remover(id);
	}
	
}
