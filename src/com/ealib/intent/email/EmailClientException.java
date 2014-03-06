package com.ealib.intent.email;

public class EmailClientException extends Exception {

	private String customMgs;

	public EmailClientException(String string) {
		this.customMgs = string;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 869520735269940659L;

	@Override
	public String getMessage() {
		return this.customMgs;
	}

}
