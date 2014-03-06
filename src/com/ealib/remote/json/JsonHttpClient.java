package com.ealib.remote.json;

import java.net.URI;

import org.json.JSONObject;

import com.ealib.json.mapper.conf.JsonMappingConfiguration;
import com.ealib.remote.HttpAbstractClientWrapper;

public abstract class JsonHttpClient<T> extends HttpAbstractClientWrapper<T> {

	protected JsonDataConverter<T> jsonDataSource;

	public JsonHttpClient(URI webServiceEndPoint,
			JsonMappingConfiguration jsonMappingConfiguration, Class<T> classT) {
		super(webServiceEndPoint);

		String simpleName = classT.getSimpleName();

		jsonDataSource = new JsonDataConverter<T>(jsonMappingConfiguration,
				simpleName) {

		};
	}

	protected JsonMappingConfiguration jsonMappingConfiguration;

	@Override
	protected T onResponseSuccessfulReceived(String stringResponse)
			throws Exception {

		JSONObject responseFullObject;

		responseFullObject = new JSONObject(stringResponse);

		T t = convertIntoJson(responseFullObject, this.jsonDataSource);
		
		return t;

	}

	protected abstract T convertIntoJson(JSONObject responseFullObject,
			JsonDataConverter<T> jsonDataSource) throws Exception;

}
