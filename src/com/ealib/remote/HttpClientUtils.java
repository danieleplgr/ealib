package com.ealib.remote;

import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	public interface ResponseErrorMessageSeeker {

		public static final String MESSAGE_NOT_EXTRACTED = "NOT_ABLE_TO_EXTRACT_MESSAGE_FROM_RESPONSE";

		String extractErrorMesssage(String responseString);

	}

	public static void checkServerResponse(URI uri, HttpResponse response,
			ResponseErrorMessageSeeker responseErrorMessageSeeker)
			throws ParseException, IOException, RemoteServerResponseException {
		int serverStatusCode;

		serverStatusCode = response.getStatusLine().getStatusCode();

		if (serverStatusCode != HttpStatus.SC_OK) {
			String responseString = "";

			responseString = EntityUtils.toString(response.getEntity());

			String errorExtractedMessage = null;

			try {
				if (responseErrorMessageSeeker != null){
					errorExtractedMessage = responseErrorMessageSeeker
							.extractErrorMesssage(responseString);
				}
			} catch (Exception e) {
				errorExtractedMessage = ResponseErrorMessageSeeker.MESSAGE_NOT_EXTRACTED;
			}

			throw new RemoteServerResponseException(uri, serverStatusCode,
					errorExtractedMessage);
		}
	}

}
