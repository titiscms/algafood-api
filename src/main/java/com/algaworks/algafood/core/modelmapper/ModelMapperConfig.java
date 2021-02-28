package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.input.ItemPedidoDTOInput;
import com.algaworks.algafood.api.v1.model.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
//		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
//		enderecoToEnderecoDTOTypeMap
//			.<String>addMapping(
//					src -> src.getCidade().getEstado().getNome(), (dest, value) -> dest.getCidade().setEstado(value));
		
		var enderecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToEnderecoDTOTypeMap
			.<String>addMapping(
					cidadeSrc -> cidadeSrc.getCidade().getNome(), (cidadeDTODest, value) -> cidadeDTODest.setCidade(value))
			.<String>addMapping(
					estadoSrc -> estadoSrc.getCidade().getEstado().getNome(), (estadoDTODest, value) -> estadoDTODest.setEstado(value));
		
		modelMapper.createTypeMap(ItemPedidoDTOInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
						
		return modelMapper;
	}
}
