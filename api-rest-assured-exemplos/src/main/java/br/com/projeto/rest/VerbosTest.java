package br.com.projeto.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;

public class VerbosTest {
	
	@Test
	public void deveSalvarUsuarioJson() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"name\": \"Júnior\", \"age\": 30 }")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Júnior"))
			.body("age", is(30))
		;
	}
	
	@Test
	public void naoDeveSalvarUsuarioSemNome() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"age\": 30 }")
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"))
		;
	}
	
	@Test
	public void deveSalvarUsuarioXML() {
		given()
			.log().all()
			.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
			.contentType(ContentType.XML)
			.body("<user><name>Júnior</name><age>30</age></user>")
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Júnior"))
			.body("user.age", is("30"))
		;
	}
	
	@Test
	public void deveAlterarUsuarioJson() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"name\": \"Usuário Alterado\", \"age\": 80 }")
		.when()
			.put("http://restapi.wcaquino.me/users/1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
		;
	}
	
	//	@Test
	//	public void deveAlterarUsuarioXML() {
	//		given()
	//			.log().all()
	//			.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
	//			.contentType(ContentType.XML)
	//			.body("<user><name>Usuário Alterado</name><age>80</age></user>")
	//		.when()
	//			.put("http://restapi.wcaquino.me/usersXML/1")
	//		.then()
	//			.log().all()
	//			.statusCode(200)
	//			.body("id", is("1"))
	//			.body("name", is("Usuário Alterado"))
	//			.body("age", is("80"))
	//			.body("salary", is("1234.5678"))
	//		;
	//	}
	
	@Test
	public void deveCustomizarURLParte1() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"name\": \"Usuário Alterado\", \"age\": 80 }")
		.when()
			.put("http://restapi.wcaquino.me/{entidade}/{userId}", "users", "1")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
		;
	}
	
	@Test
	public void deveCustomizarURLParte2() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{ \"name\": \"Usuário Alterado\", \"age\": 80 }")
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.put("http://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuário Alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f))
		;
	}
	
	@Test
	public void deveRemoverUsuarioJson() {
		given()
			.log().all()
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.delete("http://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.log().all()
			.statusCode(204)
		;
	}
	
	@Test
	public void naoDeveRemoverUsuarioInesistenteJson() {
		given()
			.log().all()
			.pathParam("entidade", "users")
			.pathParam("userId", 1000)
		.when()
			.delete("http://restapi.wcaquino.me/{entidade}/{userId}")
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Registro inexistente"))
		;
	}
	
}
