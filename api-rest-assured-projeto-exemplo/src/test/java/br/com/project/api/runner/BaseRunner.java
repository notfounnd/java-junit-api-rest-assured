package br.com.project.api.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.project.api.config.ApplicationProperties;
import br.com.project.api.helpers.RestAssuredBuilder;
import br.com.project.api.report.BaseExtendReport;
import br.com.project.api.report.ExtendReportInstance;

public class BaseRunner {

	private static final ApplicationProperties PROPERTIES = new ApplicationProperties();
	private static BaseExtendReport report = new BaseExtendReport(PROPERTIES, ExtendReportInstance.getInstance());

	@BeforeClass
	public static void baseRunnerSetUpAll() {
		System.out.println(String.format("Environment argument: %s", System.getProperty("env")));
		System.out.println(String.format("Property environment: %s", PROPERTIES.getProperty("test.environment")));
		System.out.println(String.format("-------------------------------------------------------"));
		
		RestAssuredBuilder.setRestAssured(PROPERTIES);
	}

	@AfterClass
	public static void baseRunnerTearDownAll() {
		report.getExtent().flush();
	}

}
