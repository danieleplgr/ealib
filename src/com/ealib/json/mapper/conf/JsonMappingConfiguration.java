package com.ealib.json.mapper.conf;

import java.util.HashMap;


public class JsonMappingConfiguration {

	protected HashMap<String, JsonEntityObject<?>> jsonEntityObjectMap;

	public JsonMappingConfiguration() {
		jsonEntityObjectMap = new HashMap<String, JsonEntityObject<?>>();
	}
	
	public void addEntityObject(
			JsonEntityObject<?> jsonEntityObject) {
		this.jsonEntityObjectMap.put(jsonEntityObject.getReferenceName(), jsonEntityObject);
	}

	public JsonEntityObject<?> getJsonEntityObject(String jsonEntityObjectRefName) {
		return this.jsonEntityObjectMap.get(jsonEntityObjectRefName);
	}
	
	

}
