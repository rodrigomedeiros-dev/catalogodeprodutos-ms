package com.compassouol.catalogodeprodutos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.compassouol.catalogodeprodutos.domain.model.Produto;
import com.compassouol.catalogodeprodutos.domain.repository.ProdutoRepository;
import com.compassouol.catalogodeprodutos.util.DatabaseCleaner;
import com.compassouol.catalogodeprodutos.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroProdutoIT {

	private static final int PRODUTO_ID_INEXISTENTE = 100;
	private static final int PRODUTO_ID_EXISTENTE = 1;
	private static final String TERMO_PESQUISA_PRODUTO = "pesquisar";
	private static final int MIN_PRICE = 500;
	private static final int MAX_PRICE = 1000;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private ProdutoRepository produtoRepository;
	private String jsonCorretoProdutoTeste;
	private String jsonCorretoProdutoAtualizarTeste;

	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/v1/products";

		jsonCorretoProdutoTeste = ResourceUtils.getContentFromResource(
				"/json/produto-teste.json");

		jsonCorretoProdutoAtualizarTeste = ResourceUtils.getContentFromResource(
				"/json/produto-atualizar-teste.json");

		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarProdutos() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarProduto() {
		given()
			.body(jsonCorretoProdutoTeste)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarProdutoExistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_EXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{produtoId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarProdutoInexistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{produtoId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoPesquisarProdutoExistentePorNomeOuDescricao() {
		given()
				.queryParam("q",TERMO_PESQUISA_PRODUTO)
				.accept(ContentType.JSON)
				.when()
				.get("search")
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoPesquisarProdutosFiltandoPorPrecoMinimo() {
		given()
				.queryParam("min_price",MIN_PRICE)
				.accept(ContentType.JSON)
				.when()
				.get("/search")
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoPesquisarProdutosFiltandoPorPrecoMaximo() {
		given()
				.queryParam("max_price",MAX_PRICE)
				.accept(ContentType.JSON)
				.when()
				.get("/search")
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoPesquisarProdutosFiltandoPorPrecoMinimoPrecoMaximo() {
		given()
				.queryParam("min_price",MIN_PRICE)
				.queryParam("max_price",MAX_PRICE)
				.accept(ContentType.JSON)
				.when()
				.get("/search")
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoPesquisarProdutosFiltandoPorNomeDescricaoPrecoMinimoPrecoMaximo() {
		given()
				.queryParam("q",TERMO_PESQUISA_PRODUTO)
				.queryParam("min_price",MIN_PRICE)
				.queryParam("max_price",MAX_PRICE)
				.accept(ContentType.JSON)
				.when()
				.get("/search")
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus200_QuandoAtualizarProdutoExistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_EXISTENTE)
			.body(jsonCorretoProdutoAtualizarTeste)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{produtoId}")
		.then()
		.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus400_QuandoAtualizarProdutoInexistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_INEXISTENTE)
			.body(jsonCorretoProdutoAtualizarTeste)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{produtoId}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void deveRetornarStatus204_QuandoExcluirProdutoExistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_EXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.delete("/{produtoId}")
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoExcluirProdutoInexistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.delete("/{produtoId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		Produto produto1 = new Produto();
		produto1.setName("Produto 1");
		produto1.setDescription("Descrição do produto 1");
		produto1.setPrice(BigDecimal.valueOf(259.56));
		produtoRepository.save(produto1);

		Produto produto2 = new Produto();
		produto2.setName("Produto 2");
		produto2.setDescription("Descrição do produto 2");
		produto2.setPrice(BigDecimal.valueOf(789.45));
		produtoRepository.save(produto2);

		Produto produto3 = new Produto();
		produto3.setName("Produto para pesquisar");
		produto3.setDescription("Descrição do produto para pesquisar");
		produto3.setPrice(BigDecimal.valueOf(558.25));
		produtoRepository.save(produto3);
	}

}