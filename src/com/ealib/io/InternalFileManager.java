package com.ealib.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.ealib.utils.path.PathUtils;

import android.content.Context;

public class InternalFileManager extends FileManager {

	protected Context context;

	public static String getApplicationInternalFilesDirectory(Context context) {
		return context.getFilesDir().getAbsolutePath();
	}

	public InternalFileManager(Context context) {
		this.context = context;
	}

	public static long getInternalTotalMemory(Context context,
			UnitOfMeasure unitOfMeasure) {
		String applicationInternalFilesDirectory = getApplicationInternalFilesDirectory(context);
		long totalMemoryOfPath = getTotalMemoryOfPath(unitOfMeasure,
				applicationInternalFilesDirectory);
		return totalMemoryOfPath;
	}

	public static long getInternalFreeMemory(Context context,
			UnitOfMeasure unitOfMeasure) {
		String applicationInternalFilesDirectory = getApplicationInternalFilesDirectory(context);
		long freeMemoryOfPath = getFreeMemoryOfPath(unitOfMeasure,
				applicationInternalFilesDirectory);
		return freeMemoryOfPath;
	}

	public static long getInternalBusyMemory(Context context,
			UnitOfMeasure unitOfMeasure) {
		String applicationInternalFilesDirectory = getApplicationInternalFilesDirectory(context);
		long busyMemoryOfPath = getBusyMemoryOfPath(unitOfMeasure,
				applicationInternalFilesDirectory);
		return busyMemoryOfPath;
	}

	public boolean createFile(String filename, byte[] buffer)
			throws IOException {
		boolean created = false;

		File file = new File(PathUtils.composePath(
				getApplicationInternalFilesDirectory(context), filename));
		created = file.createNewFile();
		FileOutputStream fileOutputStream = null;
		if (buffer != null) {
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(buffer);
		}
		releaseOutputStream(fileOutputStream);

		return created;
	}

	public boolean createFile(String filename) throws IOException {
		boolean created = false;
		File file = new File(PathUtils.composePath(
				getApplicationInternalFilesDirectory(context), filename));
		created = file.createNewFile();
		FileOutputStream fileOutputStream = null;
		fileOutputStream = new FileOutputStream(file);
		releaseOutputStream(fileOutputStream);
		return created;
	}

	public OutputStream getOutputStream(String filename, int contextMode)
			throws IOException {
		OutputStream openFileOutput = context.openFileOutput(filename,
				contextMode);
		return openFileOutput;
	}

	public FileInputStream getFileInputStream(String filename)
			throws FileNotFoundException {
		FileInputStream openFileInput = context.openFileInput(filename);
		return openFileInput;
	}

	public boolean createDir(String dirPath, int contextMode) {
		boolean created = false;
		if (dirPath.contains("/")) {
			File dirToCreate = getSecondLevelFileDir(dirPath, contextMode);
			created = dirToCreate.mkdir();
		} else {
			this.context.getDir(dirPath, contextMode);
			return true;
		}
		return created;
	}

	protected File getSecondLevelFileDir(String dirPath, int contextMode) {
		int end = dirPath.indexOf("/");
		int length = dirPath.length();
		String dir = dirPath.substring(0, end);

		String path = context.getDir(dir, contextMode).getAbsolutePath();
		File dirToCreate = new File(path + File.separatorChar
				+ dirPath.subSequence(end, length));
		return dirToCreate;
	}

	public boolean delete(String filename) throws FileNotFoundException {
		boolean deleted = false;
		if (filename.contains("/")) {
			String filePathDir = context.getFilesDir().getAbsolutePath();
			File file = new File(PathUtils.composePath(filePathDir, filename));
			if (!file.exists())
				throw new FileNotFoundException("File: "
						+ file.getAbsolutePath());
			deleted = file.delete();
		} else {
			this.context.deleteFile(filename);
			return true;
		}
		return deleted;
	}

	public String readFile(String filename) throws IOException {
		String filePathDir = context.getFilesDir().getAbsolutePath();
		File file = new File(PathUtils.composePath(filePathDir, filename));

		StringBuffer sb = new StringBuffer();

		FileInputStream fstream = new FileInputStream(file);

		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine = null;

		while ((strLine = br.readLine()) != null) {
			sb.append(strLine);
		}

		in.close();

		return sb.toString();
	}

	public boolean createFile(String filename, String content)
			throws IOException {
		boolean created = false;
		File file = new File(PathUtils.composePath(
				getApplicationInternalFilesDirectory(context), filename));
		created = file.createNewFile();
		FileOutputStream fileOutputStream = null;
		if (content != null) {
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(content.getBytes());
		}
		releaseOutputStream(fileOutputStream);
		return created;
	}

	public boolean existsFile(String filename) {
		try {
			this.getFile(filename);
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

	public File getFile(String filename) throws FileNotFoundException {
		String filePathDir = context.getFilesDir().getAbsolutePath();
		File file = new File(PathUtils.composePath(filePathDir, filename));
		if (file.exists()) {
			return file;
		}
		throw new FileNotFoundException();
	}

	public String getFileAbsolutePath(String filename) {
		String applicationInternalFilesDirectory = getApplicationInternalFilesDirectory(context);
		String absoluteFilePath = PathUtils.composePath(applicationInternalFilesDirectory, filename); 
		return absoluteFilePath;
	}

	public void appendNewLine(String filename) throws IOException {
		File file = this.getFile(filename);
		boolean append = true;
		FileWriter fileWriter = new FileWriter(file, append);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		writer.newLine();
		writer.flush();
		writer.close();
	}

}
