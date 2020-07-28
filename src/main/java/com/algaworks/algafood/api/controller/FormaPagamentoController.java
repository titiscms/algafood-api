package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDTODisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoDTOInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
	
	@Autowired
	private FormaPagamentoDTODisassembler formaPagamentoDTODisassembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> listar() {
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
				
		List<FormaPagamentoDTO> formasPagamentoDTO = formaPagamentoDTOAssembler.toListFormaPagamentoDTO(formasPagamento);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formasPagamentoDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id) {
		FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(id);
		
		FormaPagamentoDTO formaPagamentoDTO = formaPagamentoDTOAssembler.toFormaPagamentoDTO(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoDTO);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoDTOInput formaPagamentoDTOInput) {
		FormaPagamento formaPagamento = formaPagamentoDTODisassembler.toDomainObject(formaPagamentoDTOInput);
		
		return formaPagamentoDTOAssembler.toFormaPagamentoDTO(cadastroFormaPagamento.salvar(formaPagamento));
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoDTO atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoDTOInput formaPagamentoDTOInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.findOrFail(id);
		
		formaPagamentoDTODisassembler.copyToDomainObject(formaPagamentoDTOInput, formaPagamentoAtual);
		
		return formaPagamentoDTOAssembler.toFormaPagamentoDTO(cadastroFormaPagamento.salvar(formaPagamentoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroFormaPagamento.remover(id);
	}
}
