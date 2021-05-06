package com.project.core.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.project.core.config.ApplicationProperties;
import com.project.core.report.BaseExtendReport;
import com.project.core.report.ExtendReportInstance;

import io.restassured.RestAssured;

public abstract class BaseRunner {

	protected static final ApplicationProperties PROPERTIES = new ApplicationProperties();
	protected static BaseExtendReport report = new BaseExtendReport(PROPERTIES, ExtendReportInstance.getInstance());
	
	@BeforeClass
	public static void baseRunnerSetUpAll() {
		System.out.println(String.format("Environment argument: %s", System.getProperty("env")));
		System.out.println(String.format("Property environment: %s", PROPERTIES.getProperty("test.environment")));
		System.out.println(String.format("-------------------------------------------------------"));
		
		// RestAssuredBuilder.setRestAssured(PROPERTIES);
	}

	@AfterClass
	public static void baseRunnerTearDownAll() {
		RestAssured.reset();
		report.getExtent().flush();
	}

}
