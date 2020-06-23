package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GrupoNaoEncontradoException(Long id) {
		this(String.format("Não existe um cadastro de grupo de código %s.", id));
	}
}
