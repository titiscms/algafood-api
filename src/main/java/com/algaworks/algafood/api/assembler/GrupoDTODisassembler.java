package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.GrupoDTOInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoDTOInput grupoDTOInput) {
		return modelMapper.map(grupoDTOInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoDTOInput grupoDTOInput, Grupo grupo) {
		modelMapper.map(grupoDTOInput, grupo);
	}
}
