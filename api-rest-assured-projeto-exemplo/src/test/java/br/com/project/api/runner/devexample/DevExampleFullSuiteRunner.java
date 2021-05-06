package br.com.project.api.runner.devexample;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.project.api.runner.BaseRunner;
import br.com.project.api.tests.devexample.FailTests;
import br.com.project.api.tests.devexample.RegisterTests;
import br.com.project.api.tests.devexample.UserTests;

@RunWith(Suite.class)
@SuiteClasses({
	UserTests.class,
	RegisterTests.class,
	FailTests.class })
public class DevExampleFullSuiteRunner extends BaseRunner {
	
	@BeforeClass
	public static void suiteSetUpAll() {
		// System.out.println("Before suite");
	}

	@AfterClass
	public static void suiteTearDownAll() {
		// System.out.println("After suite");
	}

}
