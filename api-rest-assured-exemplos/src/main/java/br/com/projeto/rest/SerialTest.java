package br.com.projeto.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;

public class SerialTest {

	@Test
	public void deveSalvarUsuarioMapJson() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "Usuário Via Map");
		params.put("age", 29);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(params)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuário Via Map"))
			.body("age", is(29))
		;
	}
	
	@Test
	public void deveSalvarUsuarioObjectJson() {
		User user = new User("Usuário Via Objeto", 31);
		
		given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuário Via Objeto"))
			.body("age", is(31))
		;
	}
	
	@Test
	public void deveDesserializarObjectAoSalvarUsuarioJson() {
		User user = new User("Usuário Desserializado", 31);
		
		User usuario = given()
			.log().all()
			.contentType("application/json")
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
		;
		
		System.out.println(usuario);
		assertThat(usuario.getId(), notNullValue());
		assertEquals("Usuário Desserializado", usuario.getName());
		assertThat(usuario.getAge(), is(31));
	}
	
	@Test
	public void deveSalvarUsuarioObjectXML() {
		User user = new User("Usuário XML Via Objeto", 40);
		
		given()
			.log().all()
			.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Usuário XML Via Objeto"))
			.body("user.age", is("40"))
		;
	}
	
	@Test
	public void deveDesserializarObjectAoSalvarUsuarioXML() {
		User user = new User("Usuário Desserializado XML", 41);
		
		User usuario = given()
			.log().all()
			.config(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")))
			.contentType(ContentType.XML)
			.body(user)
		.when()
			.post("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(201)
			.extract().body().as(User.class)
		;
		
		System.out.println(usuario);
		assertThat(usuario.getId(), notNullValue());
		assertThat(usuario.getName(), is("Usuário Desserializado XML"));
		assertThat(usuario.getAge(), is(41));
		assertThat(usuario.getSalary(), nullValue());
	}
	
}
