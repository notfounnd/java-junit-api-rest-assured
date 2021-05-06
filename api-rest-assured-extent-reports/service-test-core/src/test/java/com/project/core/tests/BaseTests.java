package com.project.core.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import com.project.core.report.ExtendReportInstance;
import com.project.core.watcher.ExecutionWatcher;

public abstract class BaseTests {

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
