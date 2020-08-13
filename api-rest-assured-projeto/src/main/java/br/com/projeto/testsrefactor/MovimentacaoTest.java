package br.com.projeto.testsrefactor;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.com.projeto.core.BaseTest;
import br.com.projeto.tests.Movimentacao;
import br.com.projeto.utils.DateUtils;
import br.com.projeto.utils.Utils;

public class MovimentacaoTest extends BaseTest {
	
	@Test
	public void deveInserirMovimentacaoSucesso() {
		Movimentacao movimentacao = getMovimentacaoValida();
		
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
	public void deveValidarCamposObrigatoriosMovimentacao() {		
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
	public void naoDeveInserirMovimentacaoDataFutura() {
		Movimentacao movimentacao = getMovimentacaoValida();
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
	public void naoRemoverContaComMovimentacao() {
		Integer conta_id = Utils.getIdContaPeloNome("Conta com movimentacao");
		
		given()
			.pathParam("id", conta_id)
		.when()
			.delete("/contas/{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
	@Test
	public void deveRemoverMovimentacao() {
		Integer mov_id = Utils.getIdMovimentacaoPelaDescricao("Movimentacao para exclusao");
		
		given()
			.pathParam("id", mov_id)
		.when()
			.delete("/transacoes/{id}")
		.then()
			.statusCode(204)
		;
	}
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao movimentacao = new Movimentacao();
		
		movimentacao.setConta_id(Utils.getIdContaPeloNome("Conta para movimentacoes"));
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
