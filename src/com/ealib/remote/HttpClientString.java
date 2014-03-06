package com.ealib.remote;

import java.net.URI;

import com.ealib.remote.HttpClientUtils.ResponseErrorMessageSeeker;

public class HttpClientString extends HttpAbstractClientWrapper<String>{

	public HttpClientString(URI webServiceEndPoint) {
		super(webServiceEndPoint);
	}

	@Override
	protected ResponseErrorMessageSeeker createResponseErrorMessageSeeker() {
		return null;
	}

	@Override
	protected String onResponseSuccessfulReceived(String stringResponse) {
		return stringResponse;
	}
	
}
