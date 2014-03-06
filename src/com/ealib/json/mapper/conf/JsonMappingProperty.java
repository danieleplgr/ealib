package com.ealib.json.mapper.conf;

import com.ealib.json.mapper.JsonObjectConverterPattern;
import com.ealib.json.mapper.conversion.JsonDataType;

public class JsonMappingProperty {

	protected String jsonPropName;
	protected String objectPropName;
	protected JsonDataType type;
	protected JsonObjectConverterPattern<?> jsonObjectConverterPattern;

	public JsonMappingProperty(String jsonPropName, String objectPropName,
			JsonDataType type) {
		this.jsonPropName = jsonPropName;
		this.objectPropName = objectPropName;
		this.type = type;
	}

	public String getJsonPropName() {
		return jsonPropName;
	}

	public void setJsonPropName(String jsonPropName) {
		this.jsonPropName = jsonPropName;
	}

	public String getObjectPropName() {
		return objectPropName;
	}

	public void setObjectPropName(String objectPropName) {
		this.objectPropName = objectPropName;
	}

	public JsonDataType getType() {
		return type;
	}

	public void setType(JsonDataType type) {
		this.type = type;
	}

	public void attachJsonObjectConverterPattern(
			JsonObjectConverterPattern<?> jsonObjectConverterPattern) {
		this.jsonObjectConverterPattern = jsonObjectConverterPattern;
	}

	public JsonObjectConverterPattern<?> getJsonObjectConverterPattern() {
		return jsonObjectConverterPattern;
	}

}
