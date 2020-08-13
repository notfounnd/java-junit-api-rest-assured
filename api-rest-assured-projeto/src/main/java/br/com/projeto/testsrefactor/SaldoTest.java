package br.com.projeto.testsrefactor;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.com.projeto.core.BaseTest;
import br.com.projeto.utils.Utils;

public class SaldoTest extends BaseTest {
	
	@Test
	public void deveCalcularSaldoContas() {
		Integer conta_id = Utils.getIdContaPeloNome("Conta para saldo");
		
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == " + conta_id + "}.saldo", is("534.00"))
		;
	}
	
}
