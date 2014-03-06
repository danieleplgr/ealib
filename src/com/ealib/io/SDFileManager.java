package com.ealib.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import com.ealib.io.sd.ExternalStorageNotAvailableException;
import com.ealib.io.sd.ExternalStorageNotWriteable;

import android.os.Environment;

public class SDFileManager extends FileManager {

	private SDFileManager() { }

	public static boolean checkExternalMedia()
			throws ExternalStorageNotWriteable,
			ExternalStorageNotAvailableException {

		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// Can read and write the media
			return true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// Can only read the media
			throw new ExternalStorageNotWriteable();
		} else {
			throw new ExternalStorageNotAvailableException();
		}
	}

	public static void writeToSDFile(String filename, String directory,
			String contentString, boolean append) throws IOException {

		checkExternalMedia();

		File root = android.os.Environment.getExternalStorageDirectory();

		File dir = new File(root.getAbsolutePath() + "/" + directory);
		dir.mkdirs();
		File file = new File(dir, filename);

		try {
			FileOutputStream f = new FileOutputStream(file, append);
			PrintWriter pw = new PrintWriter(f);

			pw.write(contentString);

			pw.flush();
			pw.close();
			f.close();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}

	public static void writeToSDFile(String filename, String contentString,
			boolean append) throws IOException {
		checkExternalMedia();

		writeToSDFile(filename, "", contentString, append);
	}

	public static void createFile(String filename, boolean reCreateEmpty)
			throws IOException {

		checkExternalMedia();

		File file = getFile(filename);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			if (reCreateEmpty) {
				file.delete();
				file.createNewFile();
			}
		}
	}


	public static void createFile(String filename) throws IOException {
		createFile(filename, false);
	}
	
	public static void createFile(String filename, String directory,
			boolean reCreateEmpty) throws IOException {

		checkExternalMedia();

		File file = getFile(filename, directory);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			if (reCreateEmpty) {
				file.delete();
				file.createNewFile();
			}
		}
	}

	public static File getFile(String filename) {
		File f = new File(getSDAbsolutePath() + File.separator + filename);
		return f;
	}

	public static File getFile(String filename, String directory) {
		File f = new File(getSDAbsolutePath() + File.separator + directory
				+ File.separator + filename);
		return f;
	}

	public static long getSDFreeMemory(UnitOfMeasure unitOfMeasure) {
		String absolutePath = getSDAbsolutePath();
		long freeMemoryOfPath = getFreeMemoryOfPath(unitOfMeasure, absolutePath);
		return freeMemoryOfPath;
	}

	public static String getSDAbsolutePath() {
		File root = android.os.Environment.getExternalStorageDirectory();
		String absolutePath = root.getAbsolutePath();
		return absolutePath;
	}

	public static String read(String filename) throws IOException {
		FileManager fileManager = new FileManager();
		File file = getFile(filename);
		FileInputStream inputStream = new FileInputStream(file);
		String readFile = fileManager.readFile(inputStream);
		return readFile;
	}

	public static boolean existsFile(String filename) {
		File file = getFile(filename);
		return file.exists();
	}

	public static boolean delete(String filename) {
		File file = getFile(filename);
		return file.delete();
	}


}
