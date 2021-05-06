package com.project.core.watcher;

import org.apache.commons.lang3.StringUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExecutionWatcher extends TestWatcher {

	private ExtentTest test;
	private ExtentReports extent;

	public ExecutionWatcher(ExtentReports extent) {
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
		test.log(Status.FAIL, "Test failed");
		test.log(Status.FAIL, "With error: " + e);
	}
	
}
