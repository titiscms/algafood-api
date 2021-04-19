package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	@ApiIgnore
	@GetMapping
	public RootEntryPointDTO getRootEntryPointDTO() {
		RootEntryPointDTO rootEntryPointDTO = new RootEntryPointDTO();
		
		if (algaSecurity.podeConsultarCozinhas()) {
	        rootEntryPointDTO.add(algaLinks.linkToCozinhas("cozinhas"));
	    }
	    
	    if (algaSecurity.podePesquisarPedidos()) {
	        rootEntryPointDTO.add(algaLinks.linkToPedidos("pedidos"));
	    }
	    
	    if (algaSecurity.podeConsultarRestaurantes()) {
	        rootEntryPointDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
	    }
	    
	    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	    	
	        rootEntryPointDTO.add(algaLinks.linkToGrupos("grupos"));
	        
	        rootEntryPointDTO.add(algaLinks.linkToUsuarios("usuarios"));
	        
	        rootEntryPointDTO.add(algaLinks.linkToPermissoes("permissoes"));
	        
	    }
	    
	    if (algaSecurity.podeConsultarFormasPagamento()) {
	        rootEntryPointDTO.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
	    }
	    
	    if (algaSecurity.podeConsultarEstados()) {
	        rootEntryPointDTO.add(algaLinks.linkToEstados("estados"));
	    }
	    
	    if (algaSecurity.podeConsultarCidades()) {
	        rootEntryPointDTO.add(algaLinks.linkToCidades("cidades"));
	    }
	    
	    if (algaSecurity.podeConsultarEstatisticas()) {
	        rootEntryPointDTO.add(algaLinks.linkToEstatisticas("estatisticas"));
	    }
		
		return rootEntryPointDTO;
	}
	
	private static final class RootEntryPointDTO extends RepresentationModel<RootEntryPointDTO> { }
	
}
