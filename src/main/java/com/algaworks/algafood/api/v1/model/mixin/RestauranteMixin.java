package com.algaworks.algafood.api.v1.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class RestauranteMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime dataCadastro;
	
	@JsonIgnore
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime dataAtualização;
	
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();
}
