package br.com.projeto.rest;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;

import org.xml.sax.SAXParseException;

public class SchemaTest {

	@Test
	public void deveValidarSchemaXML() {
		given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/usersXML")
		.then()
			.log().all()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("schema/users.xsd"))
		;
	}
	
	@Test(expected=SAXParseException.class)
	public void naoDeveValidarSchemaInvalidoXML() {
		given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/invalidUsersXML")
		.then()
			.log().all()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("schema/users.xsd"))
		;
	}
	
	@Test
	public void deveValidarSchemaJSON() {
		given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/users")
		.then()
			.log().all()
			.statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/users.json"))
		;
	}
	
}
