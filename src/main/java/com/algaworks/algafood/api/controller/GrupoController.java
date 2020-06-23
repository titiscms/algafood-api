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

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoDTODisassembler;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoDTODisassembler grupoDTODisassembler;
	
	@GetMapping
	public List<GrupoDTO> listar() {
		return grupoDTOAssembler.toListGrupoDTO(grupoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public GrupoDTO buscar(@PathVariable Long id) {
		return grupoDTOAssembler.toGrupoDTO(cadastroGrupo.findOrFail(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoDTOInput grupoDTOInput) {
		Grupo grupo = grupoDTODisassembler.toDomainObject(grupoDTOInput);
		
		return grupoDTOAssembler.toGrupoDTO(cadastroGrupo.salvar(grupo));
	}
	
	@PutMapping("/{id}")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoDTOInput grupoDTOInput) {
		Grupo grupoAtual = cadastroGrupo.findOrFail(id);
		
		grupoDTODisassembler.copyToDomainObject(grupoDTOInput, grupoAtual);

		return grupoDTOAssembler.toGrupoDTO(cadastroGrupo.salvar(grupoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroGrupo.remover(id);
	}
}
