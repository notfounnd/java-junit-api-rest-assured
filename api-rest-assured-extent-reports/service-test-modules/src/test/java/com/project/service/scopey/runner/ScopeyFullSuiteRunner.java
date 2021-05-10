package com.project.service.scopey.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.project.core.helpers.RestAssuredBuilder;
import com.project.core.runner.BaseRunner;
import com.project.service.scopey.tests.FailTests;
import com.project.service.scopey.tests.RegisterTests;
import com.project.service.scopey.tests.UserTests;

@RunWith(Suite.class)
@SuiteClasses({
	UserTests.class,
	RegisterTests.class,
	FailTests.class })
public class ScopeyFullSuiteRunner extends BaseRunner {
	
	@BeforeClass
	public static void suiteSetUpAll() {
		// System.out.println("Before suite");
		String prefix = "scopey";
		
		RestAssuredBuilder.setRestAssured(prefix, PROPERTIES);
	}

	@AfterClass
	public static void suiteTearDownAll() {
		// System.out.println("After suite");
	}

}
