package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoDTOInput {

	@Valid
	@NotNull
	private RestauranteDTOInputId restaurante;
	
	@Valid
	@NotNull
	private FormaPagamentoDTOInputId formaPagamento;
		
	@Valid
	@NotNull
	private EnderecoDTOInput enderecoEntrega;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoDTOInput> itens;
}
