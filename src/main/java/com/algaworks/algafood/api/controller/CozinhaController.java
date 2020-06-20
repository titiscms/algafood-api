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

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaDTODisassembler;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaDTOInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaDTODisassembler cozinhaDTODisassembler;

	@GetMapping
	public List<CozinhaDTO> listar() {
		return cozinhaDTOAssembler.toListCozinhaDTO(cozinhaRepository.findAll());
	}

	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable Long id) {
		return cozinhaDTOAssembler.toCozinhaDTO(cadastroCozinha.findOrFail(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaDTOInput cozinhaDTOInput) {
		Cozinha cozinha = cozinhaDTODisassembler.toDomainObject(cozinhaDTOInput);
		
		return cozinhaDTOAssembler.toCozinhaDTO(cadastroCozinha.salvar(cozinha));		
	}

	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaDTOInput cozinhaDTOInput) {
		Cozinha cozinhaAtual = cadastroCozinha.findOrFail(id);
		
		cozinhaDTODisassembler.copyToDomainObject(cozinhaDTOInput, cozinhaAtual);

		return cozinhaDTOAssembler.toCozinhaDTO(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCozinha.remover(id);
	}
}
