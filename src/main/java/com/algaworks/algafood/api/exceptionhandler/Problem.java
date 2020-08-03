package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@ApiModelProperty(example = "404", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "https://algafood.com.br/recurso-nao-encontrado", position = 5)
	private String type;
	
	@ApiModelProperty(example = "Recurso não encontrado", position = 10)
	private String title;
	
	@ApiModelProperty(example = "Não existe um cadastro de cozinha de código 12.", position = 15)
	private String detail;
	
	@ApiModelProperty(example = "Não existe um cadastro de cozinha de código 12.", position = 20)
	private String userMessage;
	
	@ApiModelProperty(example = "2020-08-03T23:25:04Z", position = 25)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
	private List<Object> objects;
	
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object {
		
		@ApiModelProperty(example = "nome", position = 1)
		private String name;
		
		@ApiModelProperty(example = "nome é obrigatório", position = 5)
		private String userMessage;
	}
}
