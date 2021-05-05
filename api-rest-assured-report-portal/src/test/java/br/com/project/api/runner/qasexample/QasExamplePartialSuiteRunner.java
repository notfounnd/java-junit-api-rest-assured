package br.com.project.api.runner.qasexample;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.project.api.runner.BaseRunner;
import br.com.project.api.tests.qasexample.AccountTests;
import br.com.project.api.tests.qasexample.AuthenticationTests;
import br.com.project.api.utils.AccountUtils;

@RunWith(Suite.class)
@SuiteClasses({
	AccountTests.class,
	AuthenticationTests.class })
public class QasExamplePartialSuiteRunner extends BaseRunner {
	
	@BeforeClass
	public static void suiteSetUpAll() {
		// System.out.println("Before suite");
		AccountUtils.generateAuthorization();
		AccountUtils.resetData();
	}

	@AfterClass
	public static void suiteTearDownAll() {
		// System.out.println("After suite");
	}

}
