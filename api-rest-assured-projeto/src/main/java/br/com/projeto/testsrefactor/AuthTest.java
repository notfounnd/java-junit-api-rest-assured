package br.com.projeto.testsrefactor;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.com.projeto.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BaseTest {
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) RestAssured.requestSpecification;
		filterableRequestSpecification.removeHeader("Authorization");
		
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
	}
	
}
