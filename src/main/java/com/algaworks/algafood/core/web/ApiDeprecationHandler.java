package com.algaworks.algafood.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class ApiDeprecationHandler extends HandlerInterceptorAdapter {
	
	private static final String MENSAGEM_DEPRECATED = "Essa versão da API está depreciada e deixara de "
			+ "existir a partir de 01/01/2022. Use a versão mais atual da API.";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getRequestURI().startsWith("/v1/")) {
			response.addHeader("X-AlgaFood-Deprecated", MENSAGEM_DEPRECATED);
		}
		
		return true;
	}
	
}
