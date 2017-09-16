package com.lora.walmart.utility;

import java.io.FileReader;
import java.util.Enumeration;
import java.util.Properties;

import com.lora.walmart.testexecutor.FrameworkDriver;

public class PropertiesFile {

	public static String RunningHost = GetEnvironmentProperty("RunningHost");

	public static String GetEnvironmentProperty(String Key) {
		try {
			FileReader reader = new FileReader(System.getProperty("user.dir") + "/src/main/java/com/lora/walmart/properties/environment.properties");
			Properties properties = new Properties();
			properties.load(reader);
			return properties.getProperty(Key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}