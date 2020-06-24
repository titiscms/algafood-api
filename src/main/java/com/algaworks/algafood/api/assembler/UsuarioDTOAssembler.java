package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO toUsuarioDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toListUsuarioDTO(List<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toUsuarioDTO(usuario))
				.collect(Collectors.toList());
	}
}
