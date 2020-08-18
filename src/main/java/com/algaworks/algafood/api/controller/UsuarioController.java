package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTODisassembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.UsuarioDTOInput;
import com.algaworks.algafood.api.model.input.UsuarioDTOInputNomeEmail;
import com.algaworks.algafood.api.model.input.UsuarioDTOInputSenha;
import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@Autowired
	private UsuarioDTODisassembler usuarioDTODisassembler;
	
	@GetMapping
	public List<UsuarioDTO> listar() {
		return usuarioDTOAssembler.toListUsuarioDTO(usuarioRepository.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable(value = "usuarioId") Long id) {
		return usuarioDTOAssembler.toUsuarioDTO(cadastroUsuario.findOrFail(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody @Valid UsuarioDTOInput usuarioDTOInput) {
		Usuario usuario = usuarioDTODisassembler.toDomainObject(usuarioDTOInput);
				
		return usuarioDTOAssembler.toUsuarioDTO(cadastroUsuario.salvar(usuario));
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable(value = "usuarioId") Long id, 
			@RequestBody @Valid UsuarioDTOInputNomeEmail usuarioDTOInputNomeEmail) {
		
		Usuario usuarioAtual = cadastroUsuario.findOrFail(id);
		
		usuarioDTODisassembler.copyToDomainObject(usuarioDTOInputNomeEmail, usuarioAtual);
		
		return usuarioDTOAssembler.toUsuarioDTO(cadastroUsuario.salvar(usuarioAtual));
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable(value = "usuarioId") Long id) {
		cadastroUsuario.remover(id);
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarSenha(@PathVariable(value = "usuarioId") Long id, 
			@RequestBody @Valid UsuarioDTOInputSenha usuarioDTOInputSenha) {
		
		cadastroUsuario.alterarSenha(id, usuarioDTOInputSenha.getSenhaAtual(), usuarioDTOInputSenha.getNovaSenha());
	}
}
