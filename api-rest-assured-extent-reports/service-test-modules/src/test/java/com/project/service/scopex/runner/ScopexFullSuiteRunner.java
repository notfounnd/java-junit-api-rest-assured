package com.project.service.scopex.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.project.core.helpers.RestAssuredBuilder;
import com.project.core.runner.BaseRunner;
import com.project.service.scopex.data.AccountHelper;
import com.project.service.scopex.tests.AccountTests;
import com.project.service.scopex.tests.AuthenticationTests;
import com.project.service.scopex.tests.BalanceTests;
import com.project.service.scopex.tests.TransactionsTests;

@RunWith(Suite.class)
@SuiteClasses({
	AccountTests.class,
	TransactionsTests.class,
	BalanceTests.class,
	AuthenticationTests.class })
public class ScopexFullSuiteRunner extends BaseRunner {
	
	@BeforeClass
	public static void suiteSetUpAll() {
		// System.out.println("Before suite");
		String prefix = "scopex";
		
		RestAssuredBuilder.setRestAssured(prefix, PROPERTIES);
		AccountHelper.generateAuthorization();
		AccountHelper.resetData();
	}

	@AfterClass
	public static void suiteTearDownAll() {
		// System.out.println("After suite");
	}

}
