package com.project.core.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;

import com.project.core.helpers.EnvironmentBuilder;

public class ApplicationProperties {

	// private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
	private Properties properties = new Properties();

	public ApplicationProperties() {
		EnvironmentBuilder.setEnvironment();
		loadAplicationProperties();
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	private void loadAplicationProperties() {
		try {
			String fileProperties = "src/test/resources/properties/application.properties";
			
			// InputStream stream = this.getClass().getResourceAsStream("/properties/application.properties");
			InputStream stream = new FileInputStream(fileProperties);
			properties.load(stream);
		} catch (IOException e) {
			// logger.error("Error on trying to read properties");
			Assert.fail("Error on trying to read properties");
		}
	}

}
