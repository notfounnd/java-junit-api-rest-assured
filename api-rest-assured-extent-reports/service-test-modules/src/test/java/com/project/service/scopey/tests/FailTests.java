package com.project.service.scopey.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.project.core.helpers.JsonBuilder;
import com.project.core.tests.BaseTests;

public class FailTests extends BaseTests {
	
	private static final String PATH = "register";
	
	@Before
	public void setUp() {
		// System.out.println("Before test");
	}
	
	@After
	public void tearDown() {
		// System.out.println("After test");
	}

	@Test
	public void shouldMatchSystemRegistrationSchema() throws Exception {
		given()
			.body(JsonBuilder.readJsonFile("system-register-success.json"))
		.when()
			.post(PATH)
		.then()
			.assertThat()
				.body(matchesJsonSchemaInClasspath("json-files/fail.system-register-schema.json"));
	}
	
	@Test
	public void shouldReturnErrorInSystemRegistrationAttempting() throws Exception {
		given()
			.body(JsonBuilder.readJsonFile("system-register-error.json"))
		.when()
			.post(PATH)
		.then()
			.assertThat()
				.statusCode(400)
				.body("error", equalTo("Wrong password"));
	}
	
}
