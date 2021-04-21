package com.algaworks.algafood.core.security.authorizationserver;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.nimbusds.jose.jwk.JWKSet;

@Controller
public class JWKSetController {

	@Autowired
	private JWKSet jwkSet;
	
	@GetMapping("/.well-known/jwks.json")
	public Map<String, Object> keys() {
		return this.jwkSet.toJSONObject();
	}
	
}
