package br.com.projeto.testsrefactor;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.com.projeto.core.BaseTest;
import br.com.projeto.utils.Utils;

public class ContasTest extends BaseTest {
	
	@Test
	public void deveIncluirContaComSucesso() {
		given()
			.body("{\"nome\": \"Conta Inserida\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		Integer conta_id = Utils.getIdContaPeloNome("Conta para alterar");
		
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
	public void naoDeveIncluirContaComMesmoNome() {
		given()
			.body("{\"nome\": \"Conta mesmo nome\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
	}
	
}
