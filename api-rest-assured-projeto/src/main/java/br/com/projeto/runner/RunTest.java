package br.com.projeto.runner;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.projeto.core.BaseTest;
import br.com.projeto.testsrefactor.AuthTest;
import br.com.projeto.testsrefactor.ContasTest;
import br.com.projeto.testsrefactor.MovimentacaoTest;
import br.com.projeto.testsrefactor.SaldoTest;
import io.restassured.RestAssured;

@RunWith(Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacaoTest.class,
	SaldoTest.class,
	AuthTest.class
})
public class RunTest extends BaseTest {
	
	@BeforeClass
	public static void login() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "teste@juniorsbrissa.com");
		login.put("senha", "123456");
		
		String token = given()
			.body(login)
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
		;
		
		RestAssured.requestSpecification.header("Authorization", "JWT " + token);
		
		RestAssured.get("/reset").then().statusCode(200);
	}
	
}
