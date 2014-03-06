package com.ealib.intent.email;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmailObject {

	private Set<String> destinatari = new HashSet<String>();
	private String subject;
	private String bodyContent;
	private List<File> attachments = new ArrayList<File>();
	
	public EmailObject(String[] destinatariEmailStrings, String subject,
			String bodyContent) {
		
		for (String destinatario : destinatariEmailStrings) {
			this.destinatari.add(destinatario);
		}
		
		this.subject = subject;
		this.bodyContent = bodyContent;
	}

	public EmailObject(String destinatario, String subject,
			String bodyContent) {
		
		this.destinatari.add(destinatario);
		this.subject = subject;
		this.bodyContent = bodyContent;
	}
	
	public String getBodyContent() {
		return bodyContent;
	}
	
	public Set<String> getDestinatari() {
		return destinatari;
	}
	
	public String getSubject() {
		return subject;
	}

	public void addAttachement(File file) {
		this.attachments.add(file);
	}

	public List<File> getAttachments() {
		return attachments;
	}
	
}
