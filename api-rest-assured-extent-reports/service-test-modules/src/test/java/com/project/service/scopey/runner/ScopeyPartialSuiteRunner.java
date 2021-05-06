package com.project.service.scopey.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.project.core.helpers.RestAssuredBuilder;
import com.project.core.runner.BaseRunner;
import com.project.service.scopey.tests.FailTests;
import com.project.service.scopey.tests.UserTests;

@RunWith(Suite.class)
@SuiteClasses({
	UserTests.class,
	FailTests.class })
public class ScopeyPartialSuiteRunner extends BaseRunner {
	
	@BeforeClass
	public static void suiteSetUpAll() {
		// System.out.println("Before suite");
		
		RestAssuredBuilder.setRestAssured("scopey", PROPERTIES);
	}

	@AfterClass
	public static void suiteTearDownAll() {
		// System.out.println("After suite");
	}

}
