package com.ealib.widget.progress;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {

	public static ProgressDialog showProgressDialog(Context context, String title,String text) {
		ProgressDialog progressDialog = ProgressDialog.show(context, title, text, true);
		return progressDialog;
	}
	
}
