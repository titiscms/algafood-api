package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cozinhaService;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario; 
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cozinhaService.findOrFail(cozinhaId);
		Cidade cidade = cidadeService.findOrFail(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = findOrFail(id);
		
		restaurante.ativarRestaurante();
	}
	
	@Transactional
	public void inativar(Long id) {
		Restaurante restaurante = findOrFail(id);
		
		restaurante.inativarRestaurante();
	}
	
	@Transactional
	public void ativar(List<Long> ids) {
		ids.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(List<Long> ids) {
		ids.forEach(this::inativar);
	}
	
	@Transactional
	public void abrir(Long id) {
		Restaurante restaurante = findOrFail(id);
		
		restaurante.abrirRestaurante();
	}
	
	@Transactional
	public void fechar(Long id) {
		Restaurante restaurante = findOrFail(id);
		
		restaurante.fecharRestaurante();
	}

	@Transactional
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoException(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_RESTAURANTE_EM_USO, id));
		}
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findOrFail(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findOrFail(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamento.findOrFail(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = findOrFail(restauranteId);
		Usuario usuario = cadastroUsuario.findOrFail(usuarioId);
		
		restaurante.adicionarUsuario(usuario);
	}

	@Transactional
	public void desassociarUsuario(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = findOrFail(restauranteId);
		Usuario usuario = cadastroUsuario.findOrFail(usuarioId);
		
		restaurante.removerUsuario(usuario);;
	}
	
	public Restaurante findOrFail(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}
}
