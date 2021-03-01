package com.algaworks.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CozinhaDTOInputV2;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTODisassemblerV2 {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaDTOInputV2 cozinhaDTOInput) {
		return modelMapper.map(cozinhaDTOInput, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaDTOInputV2 cozinhaDTOInput, Cozinha cozinha) {
		modelMapper.map(cozinhaDTOInput, cozinha);
	}
}
