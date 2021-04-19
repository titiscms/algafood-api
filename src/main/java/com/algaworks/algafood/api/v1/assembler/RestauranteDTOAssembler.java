package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.input.RestauranteDTOInput;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler
	extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}
	
	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		
		modelMapper.map(restaurante, restauranteDTO);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		}
		
		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			
			if (restaurante.aberturaPermitida()) {
				restauranteDTO.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "abrir"));
			}
			
			if (restaurante.fechamentoPermitido()) {
				restauranteDTO.add(algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
			
		}
		
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			
			if (restaurante.ativacaoPermitida()) {
				restauranteDTO.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}
			
			if (restaurante.inativacaoPermitida()) {
				restauranteDTO.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "inativar"));
			}
			
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		}
        
		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), "responsaveis"));
		}
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteDTO.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}
		
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}
		
		if (algaSecurity.podeConsultarCidades() && algaSecurity.podeConsultarEstados()) {
			
			if (restauranteDTO.getEndereco() != null 
					&& restauranteDTO.getEndereco().getCidade() != null
					&& restauranteDTO.getEndereco().getEstado() != null) {
				
				restauranteDTO.getEndereco().add(algaLinks.linkToCidade(
						restaurante.getEndereco().getCidade().getId()).withRel("cidade"));
				
				restauranteDTO.getEndereco().add(algaLinks.linkToEstado(
						restaurante.getEndereco().getCidade().getEstado().getId()).withRel("estado"));
				
			}
			
		}
		
		return restauranteDTO;
	}
	
	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> restaurantes) {
		
		CollectionModel<RestauranteDTO> restaurantesDto = super.toCollectionModel(restaurantes);
		
		if (algaSecurity.podeConsultarRestaurantes()) {
			restaurantesDto.add(algaLinks.linkToRestaurantes());
		}
		
		return restaurantesDto;
	}

	public RestauranteDTOInput toRestauranteDTOInput(Restaurante restauranteAtual) {
		return modelMapper.map(restauranteAtual, RestauranteDTOInput.class);
	}
	
}
