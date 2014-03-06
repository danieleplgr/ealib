package com.ealib.json.mapper.conversion;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ealib.json.mapper.JsonObjectConverterPattern;
import com.ealib.json.mapper.conf.JsonEntityObject;
import com.ealib.json.mapper.conf.JsonMappingProperty;

public class JsonValueConverter {

	private static final Object NULL_VALUE_JSON = "null";

	public static <T> Object getValue(JsonMappingProperty prop,
			JSONObject jsonObject, JsonEntityObject<T> jsonEntityObject)
			throws JSONException {
		JsonDataType type = prop.getType();
		String jsonPropName = prop.getJsonPropName();
		if (type.equals(JsonDataType.BOOLEAN)) {
			return jsonObject.getBoolean(jsonPropName);
		}else if (type.equals(JsonDataType.STRING)) {
			String string = jsonObject.getString(jsonPropName);
			if (NULL_VALUE_JSON.equals(string)) {
				return "";
			} else {
				return string;
			}
		} else if (type.equals(JsonDataType.INTEGER)) {
			return jsonObject.getInt(jsonPropName);
		} else if (type.equals(JsonDataType.LIST)) {
			
			Object listObjectToCheckNull = jsonObject.get(jsonPropName);
			
			if (NULL_VALUE_JSON.equals(listObjectToCheckNull)) {
				return new ArrayList();
			} else {
				return jsonObject.getJSONArray(jsonPropName);
			}
			
		} else if (type.equals(JsonDataType.OBJECT)) {
			JsonObjectConverterPattern<?> jsonObjectConverterPattern = prop
					.getJsonObjectConverterPattern();
			if (jsonObjectConverterPattern == null)
				throw new UnsupportedOperationException(
						"Error in Json Conversion, cannot convert json property "
								+ jsonPropName
								+ " of type OBJECT without a valid JsonObjectConverterPattern.");
			return jsonObjectConverterPattern.convert(jsonObject
					.getJSONObject(jsonPropName));
		}

		throw new IllegalArgumentException(
				"JsonValueConverter: cant convert JsonDataType '"
						+ type
						+ "' , be sure you have defined a valid JsonDataType for jsonPropertyName with name '"
						+ jsonPropName + "'.");
	}

}
