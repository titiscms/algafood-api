package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeDTODisassembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeDTOInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeDTODisassembler cidadeDTODisassembler;
	
	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeDTOAssembler.toListCidadeDTO(cidadeRepository.findAll());
	}
	
	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable(value = "cidadeId") Long id) {
		
		return cidadeDTOAssembler.toCidadeDTO(cadastroCidade.findOrFail(id));
	}
	
	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			@RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		
		try {
			Cidade cidade = cidadeDTODisassembler.toDomainObject(cidadeDTOInput);
			
			return cidadeDTOAssembler.toCidadeDTO(cadastroCidade.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable(value = "cidadeId") Long id,
			@ApiParam(name = "corpo", value = "Representação de uma cidade como os novos dados", example = "1")
			@RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		
		try {
			Cidade cidadeAtual = cadastroCidade.findOrFail(id);
			
			cidadeDTODisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);
			
			return cidadeDTOAssembler.toCidadeDTO(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Exclui uma cidade por ID")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable(value = "cidadeId") Long id) {
		
		cadastroCidade.remover(id);
	}
}
