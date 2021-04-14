package com.algaworks.algafood.core.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			/*
			 * configuração para não manter a sessão.
			 */
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			
		.and()	
			.csrf().disable()
			.cors()
			
		.and()
			.oauth2ResourceServer()
				.jwt()
				/*
				 * configuração para reconhecer as authorities no corpo do jwt token
				 */
				.jwtAuthenticationConverter(jwtAuthenticationConverter());
		
	}

	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			
			List<String> authorities = jwt.getClaimAsStringList("authorities");
			
			if (authorities == null) {
				authorities = Collections.emptyList();
			}
			
			var scopesAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
			
			Collection<GrantedAuthority> grantedAuthorities = scopesAuthoritiesConverter.convert(jwt);
			
			List<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
					.map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			
			grantedAuthorities.addAll(simpleGrantedAuthorities);
			
			return grantedAuthorities;
		});
		
		return jwtAuthenticationConverter;
	}
	
}
