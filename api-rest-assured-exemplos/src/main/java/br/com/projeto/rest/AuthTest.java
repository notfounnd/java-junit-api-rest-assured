package br.com.projeto.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;

public class AuthTest {
	
	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().all()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		;
	}
	
	//c817410a7e3230fb45f97d434a8c6b13
	
	@Test
	public void deveObterClima() {
		given()
			.log().all()
			.queryParam("q", "Curitiba,BR")
			.queryParam("appid", "c817410a7e3230fb45f97d434a8c6b13")
			.queryParam("units","metric")
		.when()
			.get("http://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Curitiba"))
			.body("coord.lon", is(-49.29f))
			.body("main.temp", lessThan(30f))
		;
	}
	
	@Test
	public void naoDeveAcessarSemSenha() {
		given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/basicAuth")
		.then()
			.log().all()
			.statusCode(401)
		;
	}
	
	@Test
	public void deveFazerAuthBasica1() {
		given()
			.log().all()
		.when()
			.get("http://admin:senha@restapi.wcaquino.me/basicAuth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAuthBasica2() {
		given()
			.log().all()
			.auth().basic("admin", "senha")
		.when()
			.get("http://restapi.wcaquino.me/basicAuth")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAuthBasicaChallenge() {
		given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("http://restapi.wcaquino.me/basicAuth2")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAuthComTokenJWT() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "teste@juniorsbrissa.com");
		login.put("senha", "123456");
		
		//Recuperar Token
		String token = given()
			.log().all()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("http://barrigarest.wcaquino.me/signin")
		.then()
			.log().all()
			.statusCode(200)
			.extract().path("token")
		;
		
		//Consultar Contas
		given()
			.log().all()
			.header("Authorization", "JWT " + token)
		.when()
			.get("http://barrigarest.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", hasItem("Conta de Teste"))
		;
	}
	
	@Test
	public void deveAcessarAplicacaoWeb() {
		String cookie = given()
			.log().all()
			.formParam("email", "teste@juniorsbrissa.com")
			.formParam("senha", "123456")
			.contentType(ContentType.URLENC.withCharset("UTF-8"))
		.when()
			.post("http://seubarriga.wcaquino.me/logar")
		.then()
			.log().all()
			.statusCode(200)
			.extract().header("set-cookie")
		;
		
		cookie = cookie.split("=")[1].split(";")[0];
		
		String body = given()
			.log().all()
			.cookie("connect.sid", cookie)
		.when()
			.get("http://seubarriga.wcaquino.me/contas")
		.then()
			.log().all()
			.statusCode(200)
			.body("html.body.table.tbody.tr[0].td[0]", is("Conta de Teste"))
			.extract().body().asString()
		;
		
		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, body);
		System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
		MatcherAssert.assertThat(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"), is("Conta de Teste"));
	}

}
