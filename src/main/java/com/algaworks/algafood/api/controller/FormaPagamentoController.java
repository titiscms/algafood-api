package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDTODisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoDTOInput;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
	
	@Autowired
	private FormaPagamentoDTODisassembler formaPagamentoDTODisassembler;
	
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacao();
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
		               .cacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS).cachePublic())
		               .eTag(eTag)
		               .build();
		}
		
		List<FormaPagamento> formasPagamento = formaPagamentoRepository.findAll();
				
		CollectionModel<FormaPagamentoDTO> formasPagamentoDTO = formaPagamentoDTOAssembler.toCollectionModel(formasPagamento);

		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamentoDTO);
	}
	
	@GetMapping("/{FormaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable("FormaPagamentoId") Long id, ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.getDataUltimaAtualizacaoById(id);
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
					.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
					.eTag(eTag)
					.build();
		}
		
		FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(id);
		
		FormaPagamentoDTO formaPagamentoDTO = formaPagamentoDTOAssembler.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formaPagamentoDTO);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoDTOInput formaPagamentoDTOInput) {
		FormaPagamento formaPagamento = formaPagamentoDTODisassembler.toDomainObject(formaPagamentoDTOInput);
		
		return formaPagamentoDTOAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamento));
	}
	
	@PutMapping("/{FormaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable("FormaPagamentoId") Long id, @RequestBody @Valid FormaPagamentoDTOInput formaPagamentoDTOInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.findOrFail(id);
		
		formaPagamentoDTODisassembler.copyToDomainObject(formaPagamentoDTOInput, formaPagamentoAtual);
		
		return formaPagamentoDTOAssembler.toModel(cadastroFormaPagamento.salvar(formaPagamentoAtual));
	}
	
	@DeleteMapping("/{FormaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("FormaPagamentoId") Long id) {
		cadastroFormaPagamento.remover(id);
	}
}
