package com.ealib.json.mapper;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonObjectConverterPattern<T> {

	T convert(JSONObject jsonObject) throws JSONException;

}
