package com.project.service.scopex.data;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;

public class AccountHelper {

	public static Integer getIdContaPeloNome(String nome) {
		return RestAssured.get("/contas?nome=" + nome).then().extract().path("id[0]");
	}

	public static Integer getIdMovimentacaoPelaDescricao(String descricao) {
		return RestAssured.get("/transacoes?descricao=" + descricao).then().extract().path("id[0]");
	}

	public static void generateAuthorization() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "teste@juniorsbrissa.com");
		login.put("senha", "123456");

		String token = given().body(login).when().post("/signin").then().statusCode(200).extract().path("token");

		RestAssured.requestSpecification.header("Authorization", "JWT " + token);
	}

	public static void resetData() {
		RestAssured.get("/reset").then().statusCode(200);
	}

}
