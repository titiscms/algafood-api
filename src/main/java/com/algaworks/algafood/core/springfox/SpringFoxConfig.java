package com.algaworks.algafood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.EstadoDTO;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.GrupoDTO;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDTO;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.model.CidadesDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.CozinhasDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.EstadosDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.GruposDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PedidosResumoDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PermissoesDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.ProdutosDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.RestaurantesBasicoDTOOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.UsuariosDTOOpenApi;
import com.algaworks.algafood.api.v2.model.CidadeDTOV2;
import com.algaworks.algafood.api.v2.model.CozinhaDTOV2;
import com.algaworks.algafood.api.v2.openapi.model.CidadesDTOOpenApiV2;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasDTOOpenApiV2;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocketV1() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.ant("/v1/**"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class, File.class, 
						InputStream.class, Resource.class, URL.class, URI.class, URLStreamHandler.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaDTO.class), CozinhasDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class), PedidosResumoDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeDTO.class), CidadesDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, EstadoDTO.class), EstadosDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class), FormasPagamentoDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, GrupoDTO.class), GruposDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, PermissaoDTO.class), PermissoesDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, ProdutoDTO.class), ProdutosDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class), RestaurantesBasicoDTOOpenApi.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, UsuarioDTO.class), UsuariosDTOOpenApi.class))
				.apiInfo(apiInfoV1())
				.tags(tagsV1()[0], tagsV1());
	}
	
//	@Bean
	public Docket apiDocketV2() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V2")
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
					.paths(PathSelectors.ant("/v2/**"))
					.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(ServletWebRequest.class, File.class, 
						InputStream.class, Resource.class, URL.class, URI.class, URLStreamHandler.class)
				.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
				.directModelSubstitute(Links.class, LinksModelOpenApi.class)
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(PagedModel.class, CozinhaDTOV2.class), CozinhasDTOOpenApiV2.class))
				.alternateTypeRules(AlternateTypeRules.newRule(
						typeResolver.resolve(CollectionModel.class, CidadeDTOV2.class), CidadesDTOOpenApiV2.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(PagedModel.class, PedidoResumoDTO.class), PedidosResumoDTOOpenApi.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(CollectionModel.class, EstadoDTO.class), EstadosDTOOpenApi.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(CollectionModel.class, FormaPagamentoDTO.class), FormasPagamentoDTOOpenApi.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(CollectionModel.class, GrupoDTO.class), GruposDTOOpenApi.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(CollectionModel.class, PermissaoDTO.class), PermissoesDTOOpenApi.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(CollectionModel.class, ProdutoDTO.class), ProdutosDTOOpenApi.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(CollectionModel.class, RestauranteBasicoDTO.class), RestaurantesBasicoDTOOpenApi.class))
//				.alternateTypeRules(AlternateTypeRules.newRule(
//						typeResolver.resolve(CollectionModel.class, UsuarioDTO.class), UsuariosDTOOpenApi.class))
				.apiInfo(apiInfoV2())
				.tags(tagsV2()[0], tagsV2());
	}
	
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build()
			);
	} 
	
	private List<ResponseMessage> globalPostResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisicão inválida (erro cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Requisição recusada porque o corpo está em um formato não suportado")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	private List<ResponseMessage> globalPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisicão inválida (erro cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Recurso não possui representação que poderia ser aceita pelo consumidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
					.message("Requisição recusada porque o corpo está em um formato não suportado")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Requisicão inválida (erro cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build()
			);
	}
	
	private ApiInfo apiInfoV1() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API aberta para clientes e restaurantes.")
				.version("1.0")
				.contact(new Contact("Thiago Costa", "http://github.com/titiscms", "titisbackup@gmail.com"))
				.build();
	}
	
//	private ApiInfo apiInfoV1() {
//		return new ApiInfoBuilder()
//				.title("Algafood API (Depreciada)")
//				.description("API aberta para clientes e restaurantes. <br />"
//						+ "<strong>Essa versão da API está depreciada e deixara de existir a partir de 01/01/2022. "
//						+ "Use a versão mais atual da API. </strong>")
//				.version("1.0")
//				.contact(new Contact("Thiago Costa", "http://github.com/titiscms", "titisbackup@gmail.com"))
//				.build();
//	}
	
	private ApiInfo apiInfoV2() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API atualizada e otmizada para clientes e restaurantes")
				.version("2.0")
				.contact(new Contact("Thiago Costa", "http://github.com/titiscms", "titisbackup@gmail.com"))
				.build();
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	private Tag[] tagsV1() {
		return new Tag[] {
			new Tag("Cidades", "Gerencia as cidades"),
			new Tag("Cozinhas", "Gerencia as cozinhas"),
			new Tag("Grupos", "Gerencia os grupos de usuários"),
			new Tag("FormasPagamento", "Gerencia as formas de pagamento"),
			new Tag("Pedidos", "Gerencia os pedidos"),
			new Tag("Restaurantes", "Gerencia os restaurantes"),
			new Tag("Estados", "Gerencia os estados"),
			new Tag("Produtos", "Gerencia os produtos de restaurantes"),
			new Tag("Usuários", "Gerencia os usuários"),
			new Tag("Estatísticas", "Estatísticas da AlgaFood"),
			new Tag("Permissões", "Gerencia as permissões")
		};
	}
	
	private Tag[] tagsV2() {
		return new Tag[] {
			new Tag("Cidades", "Gerencia as cidades"),
			new Tag("Cozinhas", "Gerencia as cozinhas"),
//			new Tag("Grupos", "Gerencia os grupos de usuários"),
//			new Tag("FormasPagamento", "Gerencia as formas de pagamento"),
//			new Tag("Pedidos", "Gerencia os pedidos"),
//			new Tag("Restaurantes", "Gerencia os restaurantes"),
//			new Tag("Estados", "Gerencia os estados"),
//			new Tag("Produtos", "Gerencia os produtos de restaurantes"),
//			new Tag("Usuários", "Gerencia os usuários"),
//			new Tag("Estatísticas", "Estatísticas da AlgaFood"),
//			new Tag("Permissões", "Gerencia as permissões")
		};
	}
	
}
