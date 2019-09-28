package com.nagarro.utils;

import com.nagarro.controllers.LoginController;
import com.nagarro.utils.enums.Config;
import com.nagarro.utils.enums.Endpoint;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.HashMap;

import static com.nagarro.utils.enums.Config.*;

public class Common {
	public static HashMap<Config, Object> CONFIGS = new HashMap<>();

	public static Configuration getConfig(Config config) {
		if (!CONFIGS.containsKey(config) || CONFIGS.get(config) == null) {
			loadConfiguration(config);
		}
		return (Configuration) CONFIGS.get(config);
	}

	private static void loadConfiguration(Config config) {
		Log.debug("Loading configurations for config : " + config);
		try {
			Configuration configuration = loadResources(config.value());
			CONFIGS.putIfAbsent(config, configuration);
		} catch (Exception e) {
			Log.error("Common", "loadConfiguration", "Wrong config provided");
		}
	}

	public static Configuration loadResources(String propertiesFile) {
		Log.debug("Loading properties : " + propertiesFile);
		Parameters params = new Parameters();
		propertiesFile = propertiesFile + ".properties";
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
				new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
						.configure(params.properties().setFileName(propertiesFile));
		Configuration config = null;
		try {
			config = builder.getConfiguration();
		} catch (ConfigurationException cex) {
			Log.error("Common",
					"loadPropertiesFile",
					"\nProperties File : " + propertiesFile + " is not loaded due to error : \n"
							+ cex.getMessage());
		}
		return config;
	}

	public static String generateURL(Endpoint endpoint) {
		return generateURL(endpoint,false);
	}

	public static String generateURL(Endpoint endpoint, boolean isAuth) {
		Log.debug("Generating URL for endpoint : " + endpoint);
		return isAuth ? getConfig(COMMON).getString("AUTH_HOST") + "/" + endpoint.value() :
				getConfig(COMMON).getString("API_HOST") + "/" + endpoint.value();
	}

	public static String getAccessToken() {
		if(CONFIGS.get(TOKEN) != null) {
			Log.debug("Existing token found. Reusing ...");
			return (String) CONFIGS.get(TOKEN);
		}
		return generateAccessToken();
	}

	public static String generateAccessToken() {
		Log.debug("Existing token not present. Generating..");
		String accessToken = new LoginController().getAccessToken();
		CONFIGS.put(TOKEN, accessToken);
		return accessToken;
	}
}
