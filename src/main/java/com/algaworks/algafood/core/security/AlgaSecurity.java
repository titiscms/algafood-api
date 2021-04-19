package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AlgaSecurity {
	
	private static final String SCOPE_READ = "SCOPE_READ";

	private static final String SCOPE_WRITE = "SCOPE_WRITE";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		log.info("AlgaSecurity :: getUsuarioId() :: " + jwt.getClaim("usuario_id"));
		
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaRestaurante(Long restauranteId) {
		log.info("AlgaSecurity :: gerenciaRestaurante(Long restauranteId) :: inicio -> " + restauranteId);
		
		if (restauranteId == null) {
			
			log.info("AlgaSecurity :: gerenciaRestaurante(Long restauranteId) :: fim !");
			
	        return false;
	    }
		
		log.info("AlgaSecurity :: gerenciaRestaurante(Long restauranteId) :: fim -> " + restauranteId);

		return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}
	
	public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
		log.info("AlgaSecurity :: gerenciaRestaurantePedido(String codigoPedido) :: -> " + codigoPedido);
		
		return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
	}
	
	public boolean usuarioAutenticadoIgual(Long usuarioId) {
		log.info("AlgaSecurity :: ehUsuarioAutenticado(Long usuarioId) :: -> " + usuarioId);
		
//		if (getUsuarioId() != null && usuarioId != null && usuarioId.equals(getUsuarioId())) {
//			log.info("AlgaSecurity :: ehUsuarioAutenticado(Long usuarioId) :: fim -> " + usuarioId);
//			return true;
//		}
//		
//		log.info("AlgaSecurity :: ehUsuarioAutenticado(Long usuarioId) :: fim -> " + usuarioId);
//		return false;
		
		return getUsuarioId() != null && usuarioId != null && usuarioId.equals(getUsuarioId());
	}

	private boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean isAutenticado() {
	    return getAuthentication().isAuthenticated();
	}
	
	public boolean temEscopoEscrita() {
	    return hasAuthority(SCOPE_WRITE);
	}

	public boolean temEscopoLeitura() {
	    return hasAuthority(SCOPE_READ);
	}
	
	public boolean podeConsultarRestaurantes() {
	    return temEscopoLeitura() && isAutenticado();
	}

	public boolean podeGerenciarCadastroRestaurantes() {
	    return temEscopoEscrita() && hasAuthority("EDITAR_RESTAURANTES");
	}

	public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
	    return temEscopoEscrita() && (hasAuthority("EDITAR_RESTAURANTES")
	            || gerenciaRestaurante(restauranteId));
	}

	public boolean podeConsultarUsuariosGruposPermissoes() {
		log.info("AlgaSecurity :: podeEditarUsuariosGruposPermissoes() ");
		
	    return temEscopoLeitura() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podeEditarUsuariosGruposPermissoes() {
		log.info("AlgaSecurity :: podeEditarUsuariosGruposPermissoes() ");
		
	    return temEscopoEscrita() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
	}

	public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
		log.info("AlgaSecurity :: podePesquisarPedidos(Long clienteId, Long restauranteId) :: "
				+ "clienteId -> " + clienteId + " :: restauranteId ->" + restauranteId);
		
	    return temEscopoLeitura() && (hasAuthority("CONSULTAR_PEDIDOS") 
	    		|| usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
	}

	public boolean podeGerenciarPedidos(String codigoPedido) {
		log.info("AlgaSecurity :: podeGerenciarPedidos(String codigoPedido) :: -> " + codigoPedido);
		
		return hasAuthority(SCOPE_WRITE) && (hasAuthority("GERENCIAR_PEDIDOS") 
				|| gerenciaRestauranteDoPedido(codigoPedido));
	}

	public boolean podePesquisarPedidos() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarFormasPagamento() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarCidades() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarEstados() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarCozinhas() {
	    return isAutenticado() && temEscopoLeitura();
	}

	public boolean podeConsultarEstatisticas() {
	    return temEscopoLeitura() && hasAuthority("GERAR_RELATORIOS");
	}  
	
}
