package com.ealib.cache;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

import android.content.Context;

public class ObjectCacher<O extends Serializable> {

	private Context context;

	public ObjectCacher(Context context) {
		this.context = context;
	}

	public void cacheObject(O serializable, String filename) throws IOException {
		FileOutputStream fos = context.openFileOutput(filename,
				Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(serializable);
		os.close();
	}

	@SuppressWarnings("unchecked")
	public O loadObject(String filename) throws StreamCorruptedException,
			IOException, ClassNotFoundException {
		FileInputStream fis = context.openFileInput(filename);
		ObjectInputStream is = new ObjectInputStream(fis);
		O simpleClass = (O) is.readObject();
		is.close();
		return simpleClass;
	}

}
