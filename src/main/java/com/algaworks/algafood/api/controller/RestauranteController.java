package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTODisassembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.core.validation.ValidationException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRespository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	 
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteDTODisassembler restauranteDTODisassembler;
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteDTOAssembler.toListRestauranteDTO(restauranteRespository.findAll());
	}
	
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		Restaurante restaurante =  cadastroRestaurante.findOrFail(id);

		return restauranteDTOAssembler.toRestauranteDTO(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			Restaurante restaurante = restauranteDTODisassembler.toDomainObject(restauranteDTOInput);
			
			return restauranteDTOAssembler.toRestauranteDTO(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			Restaurante restauranteAtual = cadastroRestaurante.findOrFail(id);
			
			restauranteDTODisassembler.copyToDomainObject(restauranteDTOInput, restauranteAtual); 
			
			return restauranteDTOAssembler.toRestauranteDTO(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@PatchMapping("/{id}")
	public RestauranteDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteAtual = cadastroRestaurante.findOrFail(id);
		
		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		
		RestauranteDTOInput restauranteDTOInput = restauranteDTOAssembler.toRestauranteDTOInput(restauranteAtual);
		
		return atualizar(id, restauranteDTOInput);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroRestaurante.remover(id);
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		cadastroRestaurante.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		cadastroRestaurante.inativar(id);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@RequestBody List<Long> ids) {
		try {
			cadastroRestaurante.ativar(ids);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@RequestBody List<Long> ids) {
		try {
			cadastroRestaurante.inativar(ids);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		cadastroRestaurante.abrir(id);
	}
	
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		cadastroRestaurante.fechar(id);
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
	}
	
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
	
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
}
