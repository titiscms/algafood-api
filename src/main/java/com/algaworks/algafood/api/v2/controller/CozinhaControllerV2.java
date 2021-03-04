package com.algaworks.algafood.api.v2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.api.v2.assembler.CozinhaDTOAssemblerV2;
import com.algaworks.algafood.api.v2.assembler.CozinhaDTODisassemblerV2;
import com.algaworks.algafood.api.v2.model.CozinhaDTOV2;
import com.algaworks.algafood.api.v2.model.input.CozinhaDTOInputV2;
import com.algaworks.algafood.api.v2.openapi.controller.CozinhaControllerOpenApiV2;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@RestController
//@RequestMapping(path = "/v2/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaControllerV2 implements CozinhaControllerOpenApiV2 {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssemblerV2 cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaDTODisassemblerV2 cozinhaDTODisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

	@Override
	@GetMapping
	public PagedModel<CozinhaDTOV2> listar(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		
		PagedModel<CozinhaDTOV2> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaDTOAssembler);
		
		return cozinhasPagedModel;
	}

	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaDTOV2 buscar(@PathVariable(value = "cozinhaId") Long id) {
		Cozinha cozinha = cadastroCozinha.findOrFail(id);
		
		return cozinhaDTOAssembler.toModel(cozinha);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTOV2 adicionar(@RequestBody @Valid CozinhaDTOInputV2 cozinhaDTOInput) {
		Cozinha cozinha = cozinhaDTODisassembler.toDomainObject(cozinhaDTOInput);
		
		return cozinhaDTOAssembler.toModel(cadastroCozinha.salvar(cozinha));		
	}

	@Override
	@PutMapping("/{cozinhaId}")
	public CozinhaDTOV2 atualizar(@PathVariable(value = "cozinhaId") Long id, 
			@RequestBody @Valid CozinhaDTOInputV2 cozinhaDTOInput) {
		
		Cozinha cozinhaAtual = cadastroCozinha.findOrFail(id);
		
		cozinhaDTODisassembler.copyToDomainObject(cozinhaDTOInput, cozinhaAtual);

		return cozinhaDTOAssembler.toModel(cadastroCozinha.salvar(cozinhaAtual));
	}
	
	@Override
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "cozinhaId") Long id) {
		cadastroCozinha.remover(id);
	}
}
