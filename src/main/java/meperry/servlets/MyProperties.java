package meperry.servlets;

import java.util.HashMap;
import java.util.Map;

public class MyProperties {
	Map<String, String> properties = new HashMap<>();

	public void setProperty(String key, String value) {
		properties.put(key, value);
	}

	public String getProperty(String key) {
		return properties.get(key);
	}
}
