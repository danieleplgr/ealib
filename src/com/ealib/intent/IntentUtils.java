package com.ealib.intent;

import com.ealib.intent.email.EmailAttachmentNotReadableException;
import com.ealib.intent.email.EmailClientException;
import com.ealib.intent.email.EmailIntentSender;
import com.ealib.intent.email.EmailObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {

	public static void startPhoneCallIntent(Context context, String phone) {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:" + phone));
		context.startActivity(callIntent);
	}

	public static void startEmailIntent(Context context, EmailObject emailObject)
			throws EmailAttachmentNotReadableException, EmailClientException {
		EmailIntentSender emailSender = new EmailIntentSender(context);
		emailSender.sendEmail(emailObject);
	}

	public static void startSkypeCallIntent(Context context, String username) {
		Intent sky = new Intent("android.intent.action.VIEW");
		sky.setData(Uri.parse("skype:" + username));
		context.startActivity(sky);
	}

	public static void startSkypeVideoCallIntent(Context context,
			String username) {
		Intent skypeVideo = new Intent("android.intent.action.VIEW");
		skypeVideo.setData(Uri.parse("skype:" + username + "?call&video=true"));
		context.startActivity(skypeVideo);
	}

	public static void startGMapsIntent(Context context, double latitude,
			double longitude, String label) {
		String uriBegin = "geo:" + latitude + "," + longitude;
		String query = latitude + "," + longitude + "(" + label + ")";
		String encodedQuery = Uri.encode(query);
		String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
		Uri uri = Uri.parse(uriString);
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
		context.startActivity(intent);
	}

}
