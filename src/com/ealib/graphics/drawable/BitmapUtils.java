package com.ealib.graphics.drawable;

//import org.kobjects.base64.Base64;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import android.util.Base64;

public class BitmapUtils {

	public static Bitmap scaleDownBitmapForLowMemory(Bitmap bm, int newHeight,
			Context context) {

		final float densityMultiplier = context.getResources()
				.getDisplayMetrics().density;

		int h = (int) (newHeight * densityMultiplier);
		int w = (int) (h * bm.getWidth() / ((double) bm.getHeight()));

		bm = Bitmap.createScaledBitmap(bm, w, h, true);

		return bm;
	}

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);

		return resizedBitmap;
	}

	public static Bitmap getBitmapFromDrawingCache(Bitmap drawingCache, int x,
			int y, int width, int height) {
		Bitmap createdBitmap = Bitmap.createBitmap(drawingCache, x, y, width,
				height);
		return createdBitmap;
	}

	public static String convertTo64BaseString(byte[] bytes) {
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	
}
