package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaDTODisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaDTOInput;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaDTODisassembler cozinhaDTODisassembler;

	@GetMapping
	public Page<CozinhaDTO> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		List<CozinhaDTO> cozinhasDTO =  cozinhaDTOAssembler.toListCozinhaDTO(cozinhasPage.getContent());
		
		Page<CozinhaDTO> cozinhasDTOPage = new PageImpl<CozinhaDTO>(cozinhasDTO, pageable, cozinhasPage.getTotalElements());
		
		return cozinhasDTOPage;
	}

	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable(value = "cozinhaId") Long id) {
		return cozinhaDTOAssembler.toCozinhaDTO(cadastroCozinha.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaDTOInput cozinhaDTOInput) {
		Cozinha cozinha = cozinhaDTODisassembler.toDomainObject(cozinhaDTOInput);
		
		return cozinhaDTOAssembler.toCozinhaDTO(cadastroCozinha.salvar(cozinha));		
	}

	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable(value = "cozinhaId") Long id, 
			@RequestBody @Valid CozinhaDTOInput cozinhaDTOInput) {
		
		Cozinha cozinhaAtual = cadastroCozinha.findOrFail(id);
		
		cozinhaDTODisassembler.copyToDomainObject(cozinhaDTOInput, cozinhaAtual);

		return cozinhaDTOAssembler.toCozinhaDTO(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "cozinhaId") Long id) {
		cadastroCozinha.remover(id);
	}
}
