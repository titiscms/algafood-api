package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.RestauranteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AlgaSecurity {
	
	@Autowired
	private RestauranteRepository restauranteRepository;

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		
		log.info("AlgaSecurity :: getUsuarioId() :: " + jwt.getClaim("usuario_id"));
		
		return jwt.getClaim("usuario_id");
	}
	
	public boolean gerenciaRestaurante(Long restauranteId) {
		log.info("AlgaSecurity :: gerenciaRestaurante() :: " + restauranteId);
		
		return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
	}
	
}
