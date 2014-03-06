package com.ealib.remote.json;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ealib.json.mapper.JsonReflectionMapper;
import com.ealib.json.mapper.conf.JsonEntityObject;
import com.ealib.json.mapper.conf.JsonMappingConfiguration;

public abstract class JsonDataConverter<T> {
	
	protected JsonMappingConfiguration jsonMappingConf;
	protected JsonReflectionMapper<T> jsonMapper;
	
	@SuppressWarnings("unchecked")
	public JsonDataConverter(JsonMappingConfiguration jsonMappingConf, String referenceClassName) {
		this.jsonMappingConf = jsonMappingConf;
		this.jsonMapper = new JsonReflectionMapper<T>((JsonEntityObject<T>) jsonMappingConf.getJsonEntityObject(referenceClassName));
	}

	public JsonMappingConfiguration getJsonMappingConf() {
		return jsonMappingConf;
	}

	public void setJsonMappingConf(JsonMappingConfiguration jsonMappingConf) {
		this.jsonMappingConf = jsonMappingConf;
	}
	
	public T convertToModel(JSONObject jsonObject) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, JSONException {
		return jsonMapper.convertToModel(jsonObject);
	}
	
	public List<T> convertToListModel(JSONArray jsonList) throws SecurityException, IllegalArgumentException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, JSONException {
		List<T> list = new ArrayList<T>();
		int length = jsonList.length();
		
		T t = null;
		for (int i = 0; i < length; i++) {
			JSONObject jsonObject = jsonList.getJSONObject(i);
			t= this.convertToModel(jsonObject);
			list.add(t);
		}
		return list;
	}

	
}
