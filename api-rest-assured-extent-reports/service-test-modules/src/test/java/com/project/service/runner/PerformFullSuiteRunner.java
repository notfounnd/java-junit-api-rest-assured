package com.project.service.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.project.core.runner.BaseRunner;
import com.project.service.scopex.runner.ScopexFullSuiteRunner;
import com.project.service.scopey.runner.ScopeyFullSuiteRunner;

@RunWith(Suite.class)
@SuiteClasses({
	ScopeyFullSuiteRunner.class,
	ScopexFullSuiteRunner.class })
public class PerformFullSuiteRunner extends BaseRunner {
	
	@BeforeClass
	public static void suiteSetUpAll() {
		// System.out.println("Before suite");
	}

	@AfterClass
	public static void suiteTearDownAll() {
		// System.out.println("After suite");
	}

}
