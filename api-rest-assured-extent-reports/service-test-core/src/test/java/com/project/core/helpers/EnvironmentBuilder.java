package com.project.core.helpers;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;

import com.project.core.utils.ConstantUtils;

public class EnvironmentBuilder {

	private EnvironmentBuilder() {
	}

	public static void setEnvironment() {

		if (System.getProperty("env") != null) {
			String env = System.getProperty("env");
			switch (env) {
			case "DEV":
				generateEnvironmentFile("dev-application.properties");
				break;
			case "QAS":
				generateEnvironmentFile("qas-application.properties");
				break;
			default:
				Assert.fail("Environment file not exist");
				break;
			}
		} else {
			// Debugger trick
			// generateEnvironmentFile("qas-application.properties");
			Assert.fail("Environment not defined");
		}

	}

	private static void generateEnvironmentFile(String fileName) {
		try {
			File source = new File(getSourcePropertieFilePath(fileName));
			File destination = new File(getDestinationPropertieFilePath());
			File targetFolder = new File("target/test-classes/properties/application.properties");
			
			FileUtils.copyFile(source, destination);
			FileUtils.copyFile(source, targetFolder);
			
			System.out.println(String.format("-------------------------------------------------------"));
			System.out.println(String.format("Environment file: %s", fileName));
			System.out.println(String.format("Environment argument: %s", System.getProperty("env")));
			System.out.println(String.format("-------------------------------------------------------"));
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Error on generate environment file");
		}
	}

	private static String getSourcePropertieFilePath(String fileName) {
		if (fileName.endsWith(".properties")) {
			return ConstantUtils.ENVIRONMENTS_DIRECTORY + fileName;
		}

		return ConstantUtils.ENVIRONMENTS_DIRECTORY + fileName + ".properties";
	}

	private static String getDestinationPropertieFilePath() {
		return ConstantUtils.PROPERTIES_DIRECTORY + "application.properties";
	}

}
