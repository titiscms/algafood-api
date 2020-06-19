package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafood.api.core.validation.ValidationException;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.CozinhaDTOInputId;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
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
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return toListRestauranteDTO(restauranteRespository.findAll());
	}
	
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		Restaurante restaurante =  cadastroRestaurante.findOrFail(id);

		return toRestauranteDTO(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			Restaurante restaurante = toDomainObject(restauranteDTOInput);
			
			return toRestauranteDTO(cadastroRestaurante.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			Restaurante restaurante = toDomainObject(restauranteDTOInput);
			Restaurante restauranteAtual = cadastroRestaurante.findOrFail(id);
			
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
			
			return toRestauranteDTO(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}	
	}
	
	@PatchMapping("/{id}")
	public RestauranteDTO atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteAtual = cadastroRestaurante.findOrFail(id);
		
		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		
		RestauranteDTOInput restauranteDTOInput = toRestauranteDTOInput(restauranteAtual);
		
		return atualizar(id, restauranteDTOInput);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroRestaurante.remover(id);
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
	
	private RestauranteDTO toRestauranteDTO(Restaurante restaurante) {
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinha(cozinhaDTO);
		
		return restauranteDTO;
	}
	
	private List<RestauranteDTO> toListRestauranteDTO(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toRestauranteDTO(restaurante))
				.collect(Collectors.toList());
	}
	
	private Restaurante toDomainObject(RestauranteDTOInput restauranteDTOInput) {
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteDTOInput.getCozinha().getId());
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteDTOInput.getNome());
		restaurante.setTaxaFrete(restauranteDTOInput.getTaxaFrete());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
	
	private RestauranteDTOInput toRestauranteDTOInput(Restaurante restauranteAtual) {
		CozinhaDTOInputId cozinhaDTOInputId = new CozinhaDTOInputId();
		cozinhaDTOInputId.setId(restauranteAtual.getCozinha().getId());
		
		RestauranteDTOInput restauranteDTOInput = new RestauranteDTOInput();
		restauranteDTOInput.setNome(restauranteAtual.getNome());
		restauranteDTOInput.setTaxaFrete(restauranteAtual.getTaxaFrete());
		restauranteDTOInput.setCozinha(cozinhaDTOInputId);
		
		return restauranteDTOInput;
	}
}
