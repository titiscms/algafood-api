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
	
	public boolean gerenciaRestaurantePedido(String codigoPedido) {
		log.info("AlgaSecurity :: gerenciaRestaurantePedido(String codigoPedido) :: -> " + codigoPedido);
		
		return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
	}
	
}
