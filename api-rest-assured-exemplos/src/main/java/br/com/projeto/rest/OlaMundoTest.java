package br.com.projeto.rest;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTest {
	
	@Test
	public void testOlaMundo() {
		Response response = RestAssured.request(Method.GET, "http://restapi.wcaquino.me/ola");
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 200);
		Assert.assertEquals(200, response.statusCode());
		
		ValidatableResponse validatableResponse = response.then();
		validatableResponse.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, "http://restapi.wcaquino.me/ola");
		ValidatableResponse validatableResponse = response.then();
		validatableResponse.statusCode(200);
		
		get("http://restapi.wcaquino.me/ola").then().statusCode(200);
		
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void devoConhecerMatcherHamcrest() {
		MatcherAssert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat("Maria", Matchers.is("Maria"));
		Assert.assertThat(182, Matchers.is(182));
		Assert.assertThat(182, Matchers.isA(Integer.class));
		Assert.assertThat(182d, Matchers.isA(Double.class));
		Assert.assertThat(182, Matchers.greaterThan(120));
		Assert.assertThat(182, Matchers.lessThan(190));
		
		List<Integer> impares = Arrays.asList(1,3,5,7,9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1,3,5,7,9));
		assertThat(impares, containsInAnyOrder(9,3,5,7,1));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1,5,9));
		
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));
		assertThat("Joaquina", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
	}
	
	@Test
	public void devoValidarBody() {
		given()
		.when()
			.get("http://restapi.wcaquino.me/ola")
		.then()
			.statusCode(200)
			.body(is("Ola Mundo!"))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
	
}
