package com.ealib.remote;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import com.ealib.net.ConnectivityException;
import com.ealib.net.http.HttpsClientUtils;

public abstract class HttpAbstractClientWrapper<T extends Object> {

	public interface StringEntityCreator {
		String getStringEntity() throws IOException;
	}

	public HttpAbstractClientWrapper(URI webServiceEndPoint) {
		this.webServiceEndPoint = webServiceEndPoint;
	}
	
	protected URI webServiceEndPoint;

	public URI getWebServiceEndPoint() {
		return webServiceEndPoint;
	}

	protected abstract HttpClientUtils.ResponseErrorMessageSeeker createResponseErrorMessageSeeker();

	protected abstract T onResponseSuccessfulReceived(String stringResponse) throws Exception;

	public T post(StringEntityCreator stringEntityCreator,
			Map<String, String> headers) throws Exception {
		HttpClient httpclient = HttpsClientUtils
				.getClientForNotTrustedCertificates();
		return this.post(httpclient, stringEntityCreator, headers);
	}

	public T post(HttpClient httpClientToUse,
			StringEntityCreator stringEntityCreator, Map<String, String> headers)
			throws Exception {

		// CALL METHOS
		HttpPost httppost;
		HttpResponse response = null;

		httppost = new HttpPost(webServiceEndPoint);

		String stringEntityBody = stringEntityCreator.getStringEntity();
		StringEntity stringEntity = new StringEntity(stringEntityBody);

		httppost.setEntity(stringEntity);

		Set<String> keySet = headers.keySet();
		for (String key : keySet) {
			httppost.setHeader(key, headers.get(key));
		}

		try {
			response = httpClientToUse.execute(httppost);
		} catch (UnknownHostException e) {
			throw new ConnectivityException();
		}
		
		HttpClientUtils.checkServerResponse(webServiceEndPoint, response, createResponseErrorMessageSeeker());
		// END CALL

		HttpEntity entity = response.getEntity();
		String stringResponse = EntityUtils.toString(entity);

		T t = onResponseSuccessfulReceived(stringResponse);

		return t;
	}

}
