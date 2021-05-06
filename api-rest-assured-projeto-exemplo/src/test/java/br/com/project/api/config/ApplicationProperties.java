package br.com.project.api.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.project.api.helpers.EnvironmentBuilder;

public class ApplicationProperties {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);
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
			// InputStream stream = this.getClass().getResourceAsStream("/properties/application.properties");
			InputStream stream = new FileInputStream("src/test/resources/properties/application.properties");
			properties.load(stream);
		} catch (IOException e) {
			logger.error("Error on trying to read properties");
		}
	}

}
