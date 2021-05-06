package br.com.project.api.helpers;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;

import br.com.project.api.config.ApplicationProperties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

public class RestAssuredBuilder {

	private RestAssuredBuilder() {
	}

	public static void setRestAssured(ApplicationProperties PROPERTIES) {
		RestAssuredBuilder.setBaseURI(PROPERTIES.getProperty("test.base.url"));
		RestAssuredBuilder.setBasePort(PROPERTIES.getProperty("test.base.port"));
		RestAssuredBuilder.setBasePath(PROPERTIES.getProperty("test.base.path"));

		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder = RestAssuredBuilder.setBaseContentType(requestSpecBuilder, PROPERTIES.getProperty("test.base.contentType"));
		RestAssured.requestSpecification = requestSpecBuilder.build();

		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
		responseSpecBuilder = RestAssuredBuilder.setBaseMaxTimeout(responseSpecBuilder, PROPERTIES.getProperty("test.base.maxTimeout"));
		responseSpecBuilder = RestAssuredBuilder.setBaseParser(responseSpecBuilder, PROPERTIES.getProperty("test.base.parser"));
		RestAssured.responseSpecification = responseSpecBuilder.build();
		
		// RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	private static void setBaseURI(String property) {
		if (!StringUtils.isEmpty(property)) {
			RestAssured.baseURI = property;
		}
	}
	
	private static void setBasePort(String property) {
		if (!StringUtils.isEmpty(property)) {
			RestAssured.port = Integer.parseInt(property);
		}
	}
	
	private static void setBasePath(String property) {
		if (!StringUtils.isEmpty(property)) {
			RestAssured.basePath = property;
		}
	}
	
	private static RequestSpecBuilder setBaseContentType(RequestSpecBuilder requestSpecBuilder, String property) {
		if (!StringUtils.isEmpty(property)) {
			switch (property.toUpperCase()) {
			case "XML":
				requestSpecBuilder.setContentType(ContentType.XML);
				break;
			case "HTML":
				requestSpecBuilder.setContentType(ContentType.HTML);
				break;
			default:
				requestSpecBuilder.setContentType(ContentType.JSON);
				break;
			}
		}
		return requestSpecBuilder;
	}
	
	private static ResponseSpecBuilder setBaseMaxTimeout(ResponseSpecBuilder responseSpecBuilder , String property) {
		if (!StringUtils.isEmpty(property)) {
			responseSpecBuilder.expectResponseTime(Matchers.lessThan(Long.parseLong(property)));
		}
		
		return responseSpecBuilder;
	}
	
	private static ResponseSpecBuilder setBaseParser(ResponseSpecBuilder responseSpecBuilder, String property) {
		if (!StringUtils.isEmpty(property)) {
			switch (property.toUpperCase()) {
			case "XML":
				responseSpecBuilder.setDefaultParser(Parser.XML);
				break;
			case "HTML":
				responseSpecBuilder.setDefaultParser(Parser.HTML);
				break;
			default:
				responseSpecBuilder.setDefaultParser(Parser.JSON);
				break;
			}
		}
		
		return responseSpecBuilder;
	}
}
