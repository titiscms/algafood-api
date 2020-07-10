package com.algaworks.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.input.FotoProdutoDTOInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoDTOInput fotoProdutoDTOInput) {
		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoDTOInput.getArquivo().getOriginalFilename();
		
		var arquivoFoto = Path.of("\\Users\\Thiag\\Desktop\\catalogo", nomeArquivo);
		
		System.out.println(arquivoFoto);
		System.out.println(fotoProdutoDTOInput.getArquivo().getContentType());
		System.out.println(fotoProdutoDTOInput.getDescricao());
		
		try {
			fotoProdutoDTOInput.getArquivo().transferTo(arquivoFoto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
