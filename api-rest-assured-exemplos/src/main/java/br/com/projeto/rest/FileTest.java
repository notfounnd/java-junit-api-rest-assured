package br.com.projeto.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class FileTest {
	
	@Test
	public void deveGarantirEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(404) //Deveria ser 400
			.body("error", is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void deveFazerUploadArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File(System.getProperty("user.dir") + "\\src\\main\\resources\\upload\\users.pdf"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("users.pdf"))
		;
	}
	
	@Test
	public void naoDeveFazerUploadArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File(System.getProperty("user.dir") + "\\src\\main\\resources\\upload\\itext-2.1.7.jar"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().all()
			.time(lessThan(6000L))
			.statusCode(413)
		;
	}
	
	@Test
	public void naoDeveFazerDownloadArquivo() throws IOException {
		byte[] arquivo = given()
			.log().all()
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()
			.log().all()
			.statusCode(200)
			.extract().asByteArray()
		;
		
		File imagem = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\download\\file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(arquivo);
		out.close();
		
		System.out.println(imagem.length());
		assertThat(imagem.length(), lessThan(100000L));
	}
	
}
