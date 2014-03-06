package com.ealib.widget.alert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class AlertDialogUtils {

	public static AlertDialog createSimpleAlert(Context context, String title,
			String text, String btnText,
			DialogInterface.OnClickListener listener) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(text); 
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, btnText, listener);
		return alertDialog;
	}

	public static AlertDialog createConfimDialog(Context context,
			String title, String text, String cancelText, String okText,
			DialogInterface.OnClickListener okListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
		myAlertDialog.setTitle(title);
		myAlertDialog.setMessage(text);
		myAlertDialog.setPositiveButton(okText, okListener);
		myAlertDialog.setNegativeButton(cancelText, cancelListener);
		return myAlertDialog.create();
	}

	public static AlertDialog createConfimDialog(Context context,
			View view, String title, String text, String cancelText,
			String okText, DialogInterface.OnClickListener okListener,
			DialogInterface.OnClickListener cancelListener) {
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
		myAlertDialog.setTitle(title);
		myAlertDialog.setMessage(text);
		myAlertDialog.setView(view);
		myAlertDialog.setPositiveButton(okText, okListener);
		myAlertDialog.setNegativeButton(cancelText, cancelListener);
		return myAlertDialog.create();
	}

	public static AlertDialog createSimpleAlert(Context context, View view) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setView(view);
		return alertDialog;
	}

}
