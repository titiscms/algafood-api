package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDTODisassembler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoDTOInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository; 
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroProdutoService cadastroProduto;
	
	@Autowired
	private ProdutoDTOAssembler produtoDTOAssembler;

	@Autowired
	private ProdutoDTODisassembler produtoDTODisassembler;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);
		List<Produto> todosProdutos = null;
		
		if (incluirInativos) {
			todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		return produtoDTOAssembler.toListProdutoDTO(todosProdutos); 
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = cadastroProduto.findOrFail(restauranteId, produtoId);
			
		return produtoDTOAssembler.toProdutoDTO(produto);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoDTOInput produtoDTOInput) {
		Restaurante restaurante = cadastroRestaurante.findOrFail(restauranteId);
		
		Produto produto = produtoDTODisassembler.toDomainObject(produtoDTOInput);
		produto.setRestaurante(restaurante);
		 
		produto = cadastroProduto.salvar(produto);
		
		return produtoDTOAssembler.toProdutoDTO(produto);
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
			@RequestBody @Valid ProdutoDTOInput produtoDTOInput) {
			
		Produto produtoAtual = cadastroProduto.findOrFail(restauranteId, produtoId);
		
		produtoDTODisassembler.copyToDomainObject(produtoDTOInput, produtoAtual);
		
		produtoAtual = cadastroProduto.salvar(produtoAtual);
		
		return produtoDTOAssembler.toProdutoDTO(produtoAtual);
	}
}
