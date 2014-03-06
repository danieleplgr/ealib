package com.ealib.json.mapper.conf;

import java.util.List;


public class JsonEntityObject<T extends Object> {

	protected String referenceName;
	protected List<JsonMappingProperty> jsonMappingPropertiesList;
	private String fullClassName;
//	protected JsonObjectConverterPattern<T> jsonObjectConverterPattern;
	
	public JsonEntityObject(String refName, String fullClassName) {
		this.referenceName = refName;
		this.fullClassName = fullClassName;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public List<JsonMappingProperty> getJsonMappingPropertiesList() {
		return jsonMappingPropertiesList;
	}

	public void setJsonMappingPropertiesList(
			List<JsonMappingProperty> jsonMappingPropertiesList) {
		this.jsonMappingPropertiesList = jsonMappingPropertiesList;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

//	public void setJsonObjectConverterPattern(
//			JsonObjectConverterPattern<T> jsonObjectConverterPattern) {
//		this.jsonObjectConverterPattern = jsonObjectConverterPattern;
//	}
//
//	public JsonObjectConverterPattern<T> getJsonObjectConverterPattern() {
//		return jsonObjectConverterPattern;
//	}
	
}
