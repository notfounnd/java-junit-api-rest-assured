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
		// RestAssuredBuilder.setRestAssured(PROPERTIES);
	}
	
	@AfterClass
	public static void baseRunnerTearDownAll() {
		RestAssured.reset();
		report.getExtent().flush();
	}

}
