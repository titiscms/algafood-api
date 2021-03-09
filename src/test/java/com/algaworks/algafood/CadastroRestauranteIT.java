package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {
	
    private static final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
    
    private static final String MSG_INCOMPREENSIVEL_PROBLEM_TITLE = "Mensagem incompreensível";

    private static final int RESTAURANTE_ID_INEXISTENTE = 100000000;
	
	@LocalServerPort
	private int port;
	
    @Autowired
    private DatabaseCleaner databaseCleaner;
    
    @Autowired
    private CozinhaRepository cozinhaRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;
    
    @Autowired
    private CidadeRepository cidadeRepository;
	   
    private String jsonRestauranteCorreto;
    private String jsonRestauranteSemFrete;
    private String jsonRestauranteComPropriedadeInexistente;
    private String jsonRestauranteComCozinhaInexistente;
    private String jsonRestauranteTaxaFreteNegativo;
    
    private Restaurante burgerTopRestaurante;
    
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
	
    @Before(value = "")
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        jsonRestauranteCorreto = ResourceUtils.getContentFromResource(
        		"/json/correto/restaurante-thai-food.json");
        
        jsonRestauranteSemFrete = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-sem-taxa-frete.json");
        
        jsonRestauranteComPropriedadeInexistente = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-com-propriedade-inexistente.json");
        
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource(
                "/json/incorreto/restaurante-com-cozinha-inexistente.json");
        
        jsonRestauranteTaxaFreteNegativo = ResourceUtils.getContentFromResource(
				"/json/incorreto/restaurante-taxa-frete-negativo.json");
        
        databaseCleaner.clearTables();
        prepararDados();
        
        this.mockMvc = webAppContextSetup(wac).build();
    }
    
    @Test
    public void shouldRetornarStatus200_WhenConsultarRestaurantes() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
	
	@Test
	public void souldRetornarStatus201_WhenCadastrarUmRestaurante() {
		given()
			.body(jsonRestauranteCorreto)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());		
	}
	
	@Test
    public void shouldRetornarStatus400_WhenCadastrarRestauranteSemTaxaFrete() {
        given()
            .body(jsonRestauranteSemFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }
	
    @Test
    public void shouldRetornarStatus400_WhenCadastrarRestauranteComCozinhaInexistente() {
        given()
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
    }
	
    @Test
    public void shouldRetornarRespostaEStatusCorretos_WhenConsultarRestauranteExistente() {
        given()
            .pathParam("restauranteId", burgerTopRestaurante.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(burgerTopRestaurante.getNome()));
    }
	
    @Test
    public void shouldRetornarStatus404_WhenConsultarRestauranteInexistente() {
        given()
            .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{restauranteId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    public void shouldRetornarStatus400_WhenCadastrarRestauranteComPropriedadeInexistente() throws Exception {
		mockMvc.perform(post("/restaurantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRestauranteComPropriedadeInexistente)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value(MSG_INCOMPREENSIVEL_PROBLEM_TITLE)); 
    }
	
	@Test
	public void shouldMethodArgumentNotValidException_WhenCampoTaxaFreteForNegativo() throws Exception {
		mockMvc.perform(post("/restaurantes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRestauranteTaxaFreteNegativo)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value(DADOS_INVALIDOS_PROBLEM_TITLE)); 
	}
	
    private void prepararDados() {
		Estado estado = new Estado();
		estado.setNome("Minas Gerais");
		estadoRepository.save(estado);
		
		Cidade cidade = new Cidade();
		cidade.setNome("Belo Horizonte");
		cidade.setEstado(estado);
		cidadeRepository.save(cidade);
		
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);
        
        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);
        
        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    } 
}
