package com.algaworks.algafood.api.controller;

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

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoDTODisassembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoDTODisassembler grupoDTODisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> listar() {
		return grupoDTOAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@Override
	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable(value = "grupoId") Long id) {
		return grupoDTOAssembler.toModel(cadastroGrupo.findOrFail(id));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoDTOInput grupoDTOInput) {
		Grupo grupo = grupoDTODisassembler.toDomainObject(grupoDTOInput);
		
		return grupoDTOAssembler.toModel(cadastroGrupo.salvar(grupo));
	}
	
	@Override
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable(value = "grupoId") Long id, 
			@RequestBody @Valid GrupoDTOInput grupoDTOInput) {
		
		Grupo grupoAtual = cadastroGrupo.findOrFail(id);
		
		grupoDTODisassembler.copyToDomainObject(grupoDTOInput, grupoAtual);

		return grupoDTOAssembler.toModel(cadastroGrupo.salvar(grupoAtual));
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "grupoId") Long id) {
		cadastroGrupo.remover(id);
	}
}
