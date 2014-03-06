package com.ealib.intent.email;

import java.io.File;
import java.io.IOException;

public class EmailAttachmentNotReadableException extends IOException {

	private File file;

	public EmailAttachmentNotReadableException(File file) {
		this.file = file;
	}

	@Override
	public String getMessage() {
		return "The attchment file "+file.getAbsolutePath() + " is not readable, so it can't be attach to the email.";
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8215512459341538857L;

}
