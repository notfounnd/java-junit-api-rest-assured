package com.project.core.report;

import java.util.UUID;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.project.core.config.ApplicationProperties;

public class BaseExtendReport {

	private ApplicationProperties properties;

	private ExtentSparkReporter htmlReporter;
	private ExtentReports extent;

	public BaseExtendReport(ApplicationProperties properties, ExtentReports extent) {
		this.properties = properties;
		this.extent = extent;
		initializeReport();
	}

	public void initializeReport() {
		String reportPath = properties.getProperty("test.report.path") + getReportName();
		htmlReporter = new ExtentSparkReporter(reportPath);

		extent.attachReporter(htmlReporter);

		htmlReporter.config().setDocumentTitle("Extent Report - Test Summary");
		htmlReporter.config().setReportName(String.format("Test Summary - %s", properties.getProperty("test.environment").toUpperCase()));
		htmlReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");
		// htmlReporter.config().setTheme(Theme.DARK);
	}

	private String getReportName() {
		String uuid = UUID.randomUUID().toString();
		return "results-report-" + uuid + ".html";
	}

	public ExtentReports getExtent() {
		return extent;
	}

}
