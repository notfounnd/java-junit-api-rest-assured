package br.com.project.api.tests.qasexample;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.project.api.data.Transaction;
import br.com.project.api.tests.BaseTests;
import br.com.project.api.utils.AccountUtils;
import br.com.project.api.utils.DateUtils;

public class TransactionsTests extends BaseTests {
	
	@Before
	public void setUp() {
		// System.out.println("Before test");
	}
	
	@After
	public void tearDown() {
		// System.out.println("After test");
	}
	
	@Test
	public void shouldCreateTransaction() {
		Transaction movimentacao = getMovimentacaoValida();
		
		given()
			.body(movimentacao)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)
			.body("descricao", is("Descrição da movimentação"))
			.body("tipo", is("REC"))
			.body("valor", is("100.00"))
		;
	}
	
	@Test
	public void shouldValidateRequiredFieldsToCreateTransaction() {		
		given()
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg", hasItems(
					"Data da Movimentação é obrigatório",
					"Data do pagamento é obrigatório",
					"Descrição é obrigatório",
					"Interessado é obrigatório",
					"Valor é obrigatório",
					"Valor deve ser um número",
					"Conta é obrigatório",
					"Situação é obrigatório"
					))
		;
	}
	
	@Test
	public void shouldNotCreateTransactionWithFutureDate() {
		Transaction movimentacao = getMovimentacaoValida();
		movimentacao.setData_transacao(DateUtils.getDataComDiferencaDias(2));
		
		given()
			.body(movimentacao)
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		;
	}
	
	@Test
	public void shouldDeleteTransaction() {
		Integer mov_id = AccountUtils.getIdMovimentacaoPelaDescricao("Movimentacao para exclusao");
		
		given()
			.pathParam("id", mov_id)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
	}
	
	private Transaction getMovimentacaoValida() {
		Transaction movimentacao = new Transaction();
		
		movimentacao.setConta_id(AccountUtils.getIdContaPeloNome("Conta para movimentacoes"));
		movimentacao.setDescricao("Descrição da movimentação");
		movimentacao.setEnvolvido("Envolvido na movimentação");
		movimentacao.setTipo("REC");
		movimentacao.setData_transacao(DateUtils.getDataComDiferencaDias(-1));
		movimentacao.setData_pagamento(DateUtils.getDataComDiferencaDias(5));
		movimentacao.setValor(100f);
		movimentacao.setStatus(true);
		
		return movimentacao;
	}
	
}
