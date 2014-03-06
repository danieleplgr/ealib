package com.ealib.remote;

import java.net.URI;

public class RemoteServerResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1551397671590958657L;
	
	
//	private Header[] allHeaders;
	
	private String errorExtractedMessage;
	private int serverStatusCode;
	private URI uri;

	public RemoteServerResponseException(URI uri, int serverStatusCode,
			String errorExtractedMessage) {

		this.uri = uri;
		this.serverStatusCode = serverStatusCode;
		this.errorExtractedMessage = errorExtractedMessage;
	}

	@Override
	public String getMessage() {
		String m = "The server at url: " + uri.toString()
				+ " response with code: " + serverStatusCode
				+ ". Extracted message from response: " + errorExtractedMessage;
		return m;
	}

}
