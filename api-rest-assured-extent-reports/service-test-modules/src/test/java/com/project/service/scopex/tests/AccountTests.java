package com.project.service.scopex.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.project.core.tests.BaseTests;
import com.project.service.scopex.data.AccountHelper;

public class AccountTests extends BaseTests {
	
	@Before
	public void setUp() {
		// System.out.println("Before test");
	}
	
	@After
	public void tearDown() {
		// System.out.println("After test");
	}
	
	@Test
	public void shouldCreateAnAccount() {
		given()
			.body("{\"nome\": \"Conta Inserida\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void shouldUpdateAnAccount() {
		Integer conta_id = AccountHelper.getIdContaPeloNome("Conta para alterar");
		
		given()
			.body("{\"nome\": \"Conta Alterada\"}")
			.pathParam("id", conta_id)
		.when()
			.put("/contas/{id}")
		.then()
			.statusCode(200)
			.body("nome", is("Conta Alterada"))
		;
	}
	
	@Test
	public void shouldNotCreateAnAccountWithSameName() {
		given()
			.body("{\"nome\": \"Conta mesmo nome\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
	}
	
	@Test
	public void shouldNotDeleteAccountWithTransaction() {
		Integer conta_id = AccountHelper.getIdContaPeloNome("Conta com movimentacao");
		
		given()
			.pathParam("id", conta_id)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
}
