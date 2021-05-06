package br.com.project.api.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import br.com.project.api.report.ExtendReportInstance;
import br.com.project.api.watcher.ExecutionWatcher;

public class BaseTests {

	@Rule
	public ExecutionWatcher executionWatcher = new ExecutionWatcher(ExtendReportInstance.getInstance());
	
	@Before
	public void baseTestsSetUp() {
		// System.out.println("Before test base");
	}
	
	@After
	public void baseTestsTearDown() {
		// System.out.println("After test base");
	}
	
}
