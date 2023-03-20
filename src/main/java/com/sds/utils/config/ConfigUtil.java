package com.sds.utils.config;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

	private static final ConfigBean config;

	static {
		try {
			Properties prop = new Properties();
			String propFilename = "config.properties";

			InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream(propFilename);
			if (input != null) {
				prop.load(input);
				config = new ConfigBean();
				config.setUrl_base(prop.getProperty("url_base"));
				config.setEndpoint_muniversity(prop.getProperty("endpoint_muniversity"));
				config.setEndpoint_mdosen(prop.getProperty("endpoint_mdosen"));
				config.setEndpoint_muser(prop.getProperty("endpoint_muser"));
				config.setEndpoint_musergroup(prop.getProperty("endpoint_musergroup"));
				config.setEndpoint_musergroupmenu(prop.getProperty("endpoint_musergroupmenu"));
				config.setEndpoint_mmenu(prop.getProperty("endpoint_mmenu"));
				config.setEndpoint_mcust(prop.getProperty("endpoint_mcust"));
				config.setEndpoint_mcategory(prop.getProperty("endpoint_mcategory"));
				config.setEndpoint_tquest(prop.getProperty("endpoint_tquest"));
				config.setEndpoint_tproduct(prop.getProperty("endpoint_tproduct"));
				config.setEndpoint_tbook(prop.getProperty("endpoint_tbook"));
				config.setEndpoint_twishlist(prop.getProperty("endpoint_twishlist"));
				
				System.out.println("--- Initialize Configuration...");
				System.out.println("--- url_base : " + config.getUrl_base());
			} else {
				throw new FileNotFoundException("property file '" + propFilename + "' not found in the classpath");
			}
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static ConfigBean getConfig() {
		return config;
	}

}
