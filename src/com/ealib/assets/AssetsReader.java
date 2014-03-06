package com.ealib.assets;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetsReader {

	public static String readTextFile(Context context, String filename)
			throws IOException {
		InputStream inputStream = getInputStreamFromAssetManager(context,
				filename);
		StringBuffer sb = new StringBuffer();
		while (true) {
			int c = inputStream.read();
			if (c < 0)
				break;
			if (c >= 32)
				sb.append((char) c);
		}
		inputStream.close();
		return sb.toString();
	}

	public static InputStream getInputStreamFromAssetManager(Context context,
			String filename) throws IOException {
		AssetManager am = context.getResources().getAssets();
		InputStream is = am.open(filename);
		return is;
	}

}
