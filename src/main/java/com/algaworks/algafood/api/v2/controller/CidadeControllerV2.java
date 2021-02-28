package com.algaworks.algafood.api.v2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
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
import com.algaworks.algafood.api.v2.assembler.CidadeDTOAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CidadeDTODisassemblerV2;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.input.CidadeDTOInputV2;
import com.algaworks.algafood.core.web.AlgafoodMediaTypes;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = AlgafoodMediaTypes.V2_APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssemblerV2 cidadeDTOAssembler;
	
	@Autowired
	private CidadeDTODisassemblerV2 cidadeDTODisassembler;
	
	@GetMapping
	public CollectionModel<CidadeDTOV2> listar() {
		List<Cidade> cidades = cidadeRepository.findAll();
		
		return cidadeDTOAssembler.toCollectionModel(cidades);
	}
	
	@GetMapping("/{cidadeId}")
	public CidadeDTOV2 buscar(@PathVariable(value = "cidadeId") Long id) {
		Cidade cidade = cadastroCidade.findOrFail(id);
		
		return cidadeDTOAssembler.toModel(cidade);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTOV2 adicionar(@RequestBody @Valid CidadeDTOInputV2 cidadeDTOInput) {
		try {
			Cidade cidade = cidadeDTODisassembler.toDomainObject(cidadeDTOInput);
			
			CidadeDTOV2 cidadeDTO = cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidade));
					
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getCidadeId());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@PutMapping("/{cidadeId}")
	public CidadeDTOV2 atualizar(@PathVariable(value = "cidadeId") Long id,
			@RequestBody @Valid CidadeDTOInputV2 cidadeDTOInput) {
		
		try {
			Cidade cidadeAtual = cadastroCidade.findOrFail(id);
			
			cidadeDTODisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);
			
			return cidadeDTOAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "cidadeId") Long id) {
		cadastroCidade.remover(id);
	}
	
}
