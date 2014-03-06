package com.ealib.intent.email;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class EmailIntentSender {

	private Context context;

	public EmailIntentSender(Context context) {
		this.context = context;
	}

	public void sendEmail(EmailObject email) throws EmailClientException, EmailAttachmentNotReadableException {
		
		Intent i = new Intent(android.content.Intent.ACTION_SEND);
		
		Set<String> destinatari = email.getDestinatari();
		int size = destinatari.size();
		
		String[] arrayDestinatari = destinatari.toArray(new String[size]);
		
		i.setType("text/plain");
		
		i.putExtra(Intent.EXTRA_EMAIL, arrayDestinatari);
		i.putExtra(Intent.EXTRA_SUBJECT, email.getSubject());
		i.putExtra(Intent.EXTRA_TEXT, email.getBodyContent());
		
		List<File> attachments = email.getAttachments();
		
		
		if(attachments.size() > 1){
			addAttachmentList(i, attachments);			
		}else {
			addSingleAttachment(i, attachments.get(0));
		}
		
		try {
			context.startActivity(i);
			
		} catch (android.content.ActivityNotFoundException ex) {
			throw new EmailClientException("There are no email account to use!");
		}
	}

	private void addSingleAttachment(Intent i, File file) throws EmailAttachmentNotReadableException {
		if(!file.canRead()){
			throw new EmailAttachmentNotReadableException(file);
		}
		Uri _newUri = Uri.parse("file://" + file.getAbsolutePath());
		i.putExtra(Intent.EXTRA_STREAM, _newUri);
	}

	private void addAttachmentList(Intent i, List<File> attachments)
			throws EmailAttachmentNotReadableException {
		ArrayList<Uri> uris = new ArrayList<Uri>();
		
		for (File file : attachments) {
			if(!file.canRead()){
				throw new EmailAttachmentNotReadableException(file);
			}
			
			Uri _newUri = Uri.parse("file://" + file.getAbsolutePath());

			uris.add(_newUri);			
		}
		i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
	}

}
