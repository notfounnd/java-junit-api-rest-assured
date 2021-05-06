package com.project.service.scopex.tests;

import static io.restassured.RestAssured.given;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.project.core.tests.BaseTests;

import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthenticationTests extends BaseTests {
	
	@Before
	public void setUp() {
		// System.out.println("Before test");
	}
	
	@After
	public void tearDown() {
		// System.out.println("After test");
	}
	
	@Test
	public void shouldNotAccessApiWithoutAuthorizationToken() {
		FilterableRequestSpecification filterableRequestSpecification = (FilterableRequestSpecification) RestAssured.requestSpecification;
		filterableRequestSpecification.removeHeader("Authorization");
		
		given()
		.when()
			.get("/contas")
		.then()
			.statusCode(401)
		;
	}
	
}
