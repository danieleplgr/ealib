package com.ealib.json.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ealib.json.mapper.conf.JsonEntityObject;
import com.ealib.json.mapper.conf.JsonMappingProperty;
import com.ealib.json.mapper.conversion.JsonDataType;
import com.ealib.json.mapper.conversion.JsonValueConverter;

public class JsonReflectionMapper<T extends Object> {

	private JsonEntityObject<T> jsonEntityObject;

	public JsonReflectionMapper(JsonEntityObject<T> jsonEntityObject) {
		this.jsonEntityObject = jsonEntityObject;
	}

	public static String obtainMethodName(String objectPropName,
			JsonDataType type) {
		CharSequence subSequence = objectPropName.subSequence(0, 1);
		String firstUpperChar = subSequence.toString().toUpperCase();
		return "set" + firstUpperChar
				+ objectPropName.subSequence(1, objectPropName.length());
	}

	public T convertToModel(JSONObject jsonObject)
			throws IllegalAccessException, InstantiationException,
			ClassNotFoundException, SecurityException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		T t = createNewIstance(jsonEntityObject);
		Method[] methods = Class.forName(jsonEntityObject.getFullClassName())
				.getMethods();
		fillIstance(t, methods, jsonObject);
		return t;
	}

	private void fillIstance(T t, Method[] methods, JSONObject jsonObject)
			throws SecurityException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, JSONException {

		List<JsonMappingProperty> jsonMappingPropertiesList = this.jsonEntityObject
				.getJsonMappingPropertiesList();
		for (JsonMappingProperty prop : jsonMappingPropertiesList) {
			String methodName = JsonReflectionMapper.obtainMethodName(
					prop.getObjectPropName(), prop.getType());
			Method m = findMethodToCall(methods, methodName);
			try {
				m.invoke(t, JsonValueConverter.getValue(prop, jsonObject,
						jsonEntityObject));
			} catch (Exception e) {
				throw new JsonReflectionValueMapperException(t.getClass(), m, e);
			}
		}
	}

	public Method findMethodToCall(Method[] methods, String methodName)
			throws NoSuchMethodException {
		for (Method m : methods) {
			String name = m.getName();
			if (methodName.equals(name))
				return m;
		}
		throw new NoSuchMethodException(
				"JsonReflectionMapper: method with name '"
						+ methodName
						+ "' was not found. Please check the method name must be in the canonical get/set form.");
	}

	private T createNewIstance(JsonEntityObject<T> jsonEntityObject)
			throws IllegalAccessException, InstantiationException,
			ClassNotFoundException {
		String fullClassName = jsonEntityObject.getFullClassName();
		@SuppressWarnings("unchecked")
		T newInstance = (T) Class.forName(fullClassName).newInstance();
		return newInstance;
	}
}
