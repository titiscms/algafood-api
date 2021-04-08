package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
		.and()
			/*
			 * configuração para não manter a sessão.
			 */
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.cors()
		.and()
			.oauth2ResourceServer().jwt();
	}
	
	/*
	 * configuração de chave simétrica
	 */
//	@Bean
//	public JwtDecoder jwtDecoder() {
//		
//		SecretKeySpec secretKey = new SecretKeySpec("oaiheknadcliaecadkcfkvnefoidfhdbs98euonwdnvlksjoi3".getBytes(), "HmacSHA256");
//		
//		return NimbusJwtDecoder.withSecretKey(secretKey).build();
//	}
	
}
