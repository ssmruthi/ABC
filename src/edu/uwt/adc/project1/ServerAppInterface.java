package edu.uwt.adc.project1;

import java.util.Map;

public interface ServerAppInterface {
	
	public Map<String, String> getKeyValueStore();
	public String put(String key, String value);
	public String get(String key);
	public String delete(String key);
}