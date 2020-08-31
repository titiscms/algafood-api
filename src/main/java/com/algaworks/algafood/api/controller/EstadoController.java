package com.algaworks.algafood.api.controller;

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

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoDTODisassembler;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoDTOInput;
import com.algaworks.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@Autowired
	private EstadoDTODisassembler estadoDTODisassembler;
	
	@GetMapping
	public CollectionModel<EstadoDTO> listar() {
		List<Estado> estados = estadoRepository.findAll();
		
		return estadoDTOAssembler.toCollectionModel(estados);
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable(value = "estadoId") Long id) {
		return estadoDTOAssembler.toModel(cadastroEstado.findOrFail(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoDTOInput estadoDTOInput) {
		Estado estado = estadoDTODisassembler.toObjetoDomain(estadoDTOInput);
		
		return estadoDTOAssembler.toModel(cadastroEstado.salvar(estado));
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable(value = "estadoId") Long id, @RequestBody @Valid EstadoDTOInput estadoDTOInput) {
		Estado estadoAtual = cadastroEstado.findOrFail(id);

		estadoDTODisassembler.copyToObjetoDomain(estadoDTOInput, estadoAtual);
			
		return estadoDTOAssembler.toModel(cadastroEstado.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "estadoId") Long id) {
		cadastroEstado.remover(id);	
	}
}
