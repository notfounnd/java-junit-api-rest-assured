package com.project.service.scopey.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.project.core.helpers.JsonBuilder;
import com.project.core.tests.BaseTests;

public class UserTests extends BaseTests {

	private static final String PATH = "users";
	private static final String PATH_VARIABLE = "users/2";
	private static final String PATH_VARIABLE_NOT_FOUND = "users/23";

	@Before
	public void setUp() {
		// System.out.println("Before test");
	}
	
	@After
	public void tearDown() {
		// System.out.println("After test");
	}

	@Test
	public void shouldMatchCreateUserSchema() throws Exception {
		given()
			.body(JsonBuilder.readJsonFile("create-user-success.json"))
		.when()
			.post(PATH)
		.then()
			.assertThat()
				.body(matchesJsonSchemaInClasspath("json-files/create-user-schema.json"));
	}
	
	@Test
	public void shouldGetEmptyResponseInUserSearch() throws Exception {
		given()
		.when()
			.get(PATH_VARIABLE_NOT_FOUND)
		.then()
			.assertThat()
				.statusCode(404)
				.body(equalTo("{}"));
	}
	
	@Test
	public void shouldUpdateUserInfo() throws Exception {
		given()
			.body(JsonBuilder.readJsonFile("update-user-success.json"))
		.when()
			.put(PATH_VARIABLE)
		.then()
			.assertThat()
				.body("name", equalTo("Rachel Green Geller"));
	}
	
	@Test
	public void shouldGetUsersListPagination() {
		given()
			.param("page", 2)
		.when()
			.get(PATH)
		.then()
			.assertThat()
				.body(containsString("total_pages"));
	}

}
