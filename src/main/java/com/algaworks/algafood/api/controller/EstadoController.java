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

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoDTODisassembler;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoDTOInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@Autowired
	private EstadoDTODisassembler estadoDTODisassembler;
	
	@GetMapping
	public List<EstadoDTO> listar() {
		return estadoDTOAssembler.toListEstadoDTO(estadoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable Long id) {
		return estadoDTOAssembler.toEstadoDTO(cadastroEstado.findOrFail(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoDTOInput estadoDTOInput) {
		Estado estado = estadoDTODisassembler.toObjetoDomain(estadoDTOInput);
		
		return estadoDTOAssembler.toEstadoDTO(cadastroEstado.salvar(estado));
	}
	
	@PutMapping("/{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoDTOInput estadoDTOInput) {
		Estado estadoAtual = cadastroEstado.findOrFail(id);

		estadoDTODisassembler.copyToObjetoDomain(estadoDTOInput, estadoAtual);
			
		return estadoDTOAssembler.toEstadoDTO(cadastroEstado.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroEstado.remover(id);	
	}
}
