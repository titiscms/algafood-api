package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.ProdutoDTOInput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoDTOInput produtoDTOInput) {
		return modelMapper.map(produtoDTOInput, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoDTOInput produtoDTOInput, Produto produto) {
		modelMapper.map(produtoDTOInput, produto);
	}
}
