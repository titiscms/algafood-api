package com.algaworks.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeDTODisassembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeDTOInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeDTODisassembler cidadeDTODisassembler;
	
	@Override
	@GetMapping
	public List<CidadeDTO> listar() {
		return cidadeDTOAssembler.toListCidadeDTO(cidadeRepository.findAll());
	}
	
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable(value = "cidadeId") Long id) {
		Cidade cidade = cadastroCidade.findOrFail(id);
		
		CidadeDTO cidadeDTO = cidadeDTOAssembler.toCidadeDTO(cidade);
		
		cidadeDTO.add(linkTo(CidadeController.class).slash(cidadeDTO.getId()).withSelfRel());
//		cidadeDTO.add(new Link("http://localhost:8080/cidades/" + id, IanaLinkRelations.SELF));
//		cidadeDTO.add(new Link("http://localhost:8080/cidades/" + id));

		cidadeDTO.add(linkTo(CidadeController.class).withRel("cidades"));
//		cidadeDTO.add(new Link("http://localhost:8080/cidades", IanaLinkRelations.COLLECTION));
//		cidadeDTO.add(new Link("http://localhost:8080/cidades", "cidades"));
		
		cidadeDTO.getEstado().add(linkTo(EstadoController.class).slash(cidadeDTO.getEstado().getId()).withSelfRel());
//		cidadeDTO.getEstado().add(new Link("http://localhost:8080/estados/" + cidade.getEstado().getId(), IanaLinkRelations.SELF));
//		cidadeDTO.getEstado().add(new Link("http://localhost:8080/estados/" + cidade.getEstado().getId()));
		
		return cidadeDTO;
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		try {
			Cidade cidade = cidadeDTODisassembler.toDomainObject(cidadeDTOInput);
			
			CidadeDTO cidadeDTO = cidadeDTOAssembler.toCidadeDTO(cadastroCidade.salvar(cidade));
					
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable(value = "cidadeId") Long id,
			@RequestBody @Valid CidadeDTOInput cidadeDTOInput) {
		
		try {
			Cidade cidadeAtual = cadastroCidade.findOrFail(id);
			
			cidadeDTODisassembler.copyToDomainObject(cidadeDTOInput, cidadeAtual);
			
			return cidadeDTOAssembler.toCidadeDTO(cadastroCidade.salvar(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "cidadeId") Long id) {
		cadastroCidade.remover(id);
	}
}
