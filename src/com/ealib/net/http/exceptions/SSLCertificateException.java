package com.ealib.net.http.exceptions;

public class SSLCertificateException extends Exception {

	private String customMessage;

	public SSLCertificateException(String message) {
		this.customMessage = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -516929807675860504L;

	@Override
	public String getMessage() {
		return this.customMessage;
	}
	
}
