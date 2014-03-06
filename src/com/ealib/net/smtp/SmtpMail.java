package com.ealib.net.smtp;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SmtpMail extends javax.mail.Authenticator {

	public static class SmtpInfo {

		private String host = "";
		private String port = "465";
		private String sPort = "465";
		private String user;
		private String password;
		private boolean authenticationSmtpEnable = true;
		private boolean debugMode = false;

		public SmtpInfo(String host, String port, String sPort, String user,
				String password) {
			super();
			this.host = host;
			this.port = port;
			this.sPort = sPort;
			this.user = user;
			this.password = password;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public String getPort() {
			return port;
		}

		public void setPort(String port) {
			this.port = port;
		}

		public String getsPort() {
			return sPort;
		}

		public void setsPort(String sPort) {
			this.sPort = sPort;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public boolean isAuthenticationSmtpEnable() {
			return authenticationSmtpEnable;
		}

		public void setAuthenticationSmtpEnable(boolean authenticationSmtpEnable) {
			this.authenticationSmtpEnable = authenticationSmtpEnable;
		}

		public boolean isDebugMode() {
			return debugMode;
		}

		public void setDebugMode(boolean debugMode) {
			this.debugMode = debugMode;
		}
	}

	public static class EmailInfo {

		private String[] recipients;
		private String sender;
		private String subject;
		private String body;

		public EmailInfo(String[] recipients, String sender, String subject,
				String body) {
			super();
			this.recipients = recipients;
			this.sender = sender;
			this.subject = subject;
			this.body = body;
		}

		public String[] getRecipients() {
			return recipients;
		}

		public void setRecipients(String[] recipients) {
			this.recipients = recipients;
		}

		public String getSender() {
			return sender;
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}
	}

	private class StmpMailConfigurationException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8219445848873323978L;
		private String message;
		
		public StmpMailConfigurationException(String message) {
			this.message = message;
		}

		@Override
		public String getMessage() {
			return message;
		}
		
		
	}
	
	private SmtpInfo stmpInfo;
	private EmailInfo emailInfo; 

	private Multipart _multipart;
 
	public SmtpMail(SmtpInfo _stmpInfo, EmailInfo _emailInfo) {

		if (_stmpInfo == null)
			throw new IllegalArgumentException(
					"StmpInfo argument cannot be null when instancing a new SmtpMail object");

		this.stmpInfo = _stmpInfo;
		this.emailInfo = _emailInfo;
		  
		_multipart = new MimeMultipart();

		// There is something wrong with MailCap, javamail can not find a
		// handler for the multipart/mixed part, so this bit needs to be added.
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap
				.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
	}

	public boolean send() throws Exception {
		Properties props = _setProperties();

		String _user = this.stmpInfo.getUser();
		String _pass = this.stmpInfo.getPassword();
		String _port = this.stmpInfo.getPort();
		String _sPort = this.stmpInfo.getsPort();
		String host = this.stmpInfo.getHost();
		
		String[] recipients = emailInfo.getRecipients();
		String _body = emailInfo.getBody();
		String _subject = emailInfo.getSubject();
		String _sender = emailInfo.getSender();
		
		if (checkSmtpConfigData(_user, _pass, host, _port, _sPort) && checkEmailInfo() ) {
			Session session = Session.getInstance(props, this);

			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(_sender));

			InternetAddress[] addressTo = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

			msg.setSubject(_subject);
			msg.setSentDate(new Date());

			// setup message body
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(_body);
			_multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			msg.setContent(_multipart);

			// send email
			Transport.send(msg);

			return true;
		} else {
			return false;
		}
	}

	private boolean checkEmailInfo() {
		if(emailInfo.getRecipients() != null && emailInfo.getRecipients().length > 0){
			if(emailInfo.getSender() != null && !emailInfo.getSender().equals("")){
				if(emailInfo.getBody()!=null || emailInfo.getSubject()!=null){
					if(!emailInfo.getBody().equals("") || !emailInfo.getSubject().equals("")){
						return true;
					}	
				}
			}
		}
		return false;
	}

	private boolean checkSmtpConfigData(String _user, String _pass, String hostname, String port, String sPort) {
		boolean res = !_user.equals("") && !_pass.equals("") 
				&& !"".equals(hostname) && !"".equals(port) && !"".equals(sPort)  ;
		
		if(!res)
			throw new StmpMailConfigurationException("Uncorrect configuration of the "+SmtpInfo.class.getName());
		
		return res;
	}

	public void addAttachment(File file) throws Exception {
		if(file == null)
			throw new IllegalArgumentException("File argument cannot be null in addAttachment method of "+this.getClass().getCanonicalName());
		
		BodyPart messageBodyPart = new MimeBodyPart(); 
		String name = file.getName();
		DataSource source = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(name);

		_multipart.addBodyPart(messageBodyPart);
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		String _user = this.stmpInfo.getUser();
		String _password = this.stmpInfo.getPassword();
		
		PasswordAuthentication passwordAuthentication = new PasswordAuthentication(_user, _password);
		return passwordAuthentication;
	}

	private Properties _setProperties() {
		Properties props = new Properties();
		
		String _host = this.stmpInfo.getHost();
		boolean debugMode = this.stmpInfo.isDebugMode();
		boolean authenticationSmtpEnable = this.stmpInfo.isAuthenticationSmtpEnable();
		String _port = this.stmpInfo.getPort();
		String _sPort = this.stmpInfo.getsPort();
		
		props.put("mail.smtp.host", _host);

		if (debugMode) {
			props.put("mail.debug", "true");
		}

		if (authenticationSmtpEnable) {
			props.put("mail.smtp.auth", "true");
		}

		props.put("mail.smtp.port", _port);
		props.put("mail.smtp.socketFactory.port", _sPort);
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		return props;
	}

}
