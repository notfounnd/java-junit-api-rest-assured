package com.project.core.watcher;

import java.io.PrintStream;
import java.nio.charset.Charset;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class ExecutionWatcher extends TestWatcher {

	private ExtentTest test;
	private ExtentReports extent;
	
	ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
	ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
	PrintStream requestPrint = new PrintStream(requestStream, true);
	PrintStream responsePrint = new PrintStream(responseStream, true);
	RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(requestPrint);
	ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(responsePrint);

	public ExecutionWatcher(ExtentReports extent) {
		RestAssured.filters(requestLoggingFilter, responseLoggingFilter);
		this.extent = extent;
	}

	@Override
	protected void starting(Description description) {
		String testCaseName = className(description.getClassName()) + "." + description.getMethodName();
		String categoryName = packageName(description.getClassName());
		test = extent.createTest(testCaseName).assignCategory(categoryName);
	}

	private String className(String fullClassName) {
		String[] split = fullClassName.split("tests");
		return split[1].substring(1);
	}
	
	private String packageName(String fullClassName) {
		String[] split = fullClassName.split("tests");
		String substring = split[1];
		return StringUtils.removeEnd(fullClassName, substring);
	}

	@Override
	protected void succeeded(Description description) {
		test.log(Status.PASS, "Test executed successfully");
	}

	@Override
	protected void failed(Throwable e, Description description) {
		String resquest = requestStream.toString(Charset.defaultCharset());
		String response = responseStream.toString(Charset.defaultCharset());
		
		logFailed(resquest, response, e, description);
		
		test.log(Status.FAIL, "Test failed");
		test.log(Status.INFO, MarkupHelper.createCodeBlock("With error: " + e));
		test.log(Status.INFO, MarkupHelper.createCodeBlock(resquest));
		test.log(Status.INFO, MarkupHelper.createCodeBlock(response));
	}
	
	private void logFailed(String resquest, String response, Throwable e, Description description) {
		String ls = System.lineSeparator();
		System.out.println("-[TEST FAILED]-----------------------------------------");
		System.out.println(String.format("-[METHOD] %s", description));
		System.out.println(String.format("%s", e));
		System.out.println(String.format("-[REQUEST] %s%s", ls, resquest));
		System.out.println(String.format("-[RESPONSE] %s%s", ls, response));
		System.out.println("-------------------------------------------------------");
	}
	
}
