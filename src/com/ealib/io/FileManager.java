package com.ealib.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Build;
import android.os.StatFs;

public class FileManager {

	// public static final int KB = 11;
	// public static final int BYTES = 10;
	// public static final int MB = 12;

	public enum UnitOfMeasure {
		MB, KB, BYTES
	}

	public FileManager() {
	}

	private static class CompatibilyStatFs {
		private static long getCorrectBlockSize(StatFs statFs) {
			long result = 0;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				long sdAvailSize = statFs.getBlockSizeLong();
				result = sdAvailSize;
			} else {
				@SuppressWarnings("deprecation")
				int sdAvailSize = statFs.getBlockSize();
				result = sdAvailSize;
			}
			return result;
		}

		private static long getCorrectBlockCount(StatFs statFs) {
			long result = 0;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				long sdAvailSize = statFs.getBlockCountLong();
				result = sdAvailSize;
			} else {
				@SuppressWarnings("deprecation")
				int sdAvailSize = statFs.getBlockCount();
				result = sdAvailSize;
			}
			return result;
		}

		private static long getCorrectAvailableBlocks(StatFs statFs) {
			long result = 0;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				long sdAvailSize = statFs.getAvailableBlocksLong();
				result = sdAvailSize;
			} else {
				@SuppressWarnings("deprecation")
				int sdAvailSize = statFs.getAvailableBlocks();
				result = sdAvailSize;
			}
			return result;
		}

	}

	public String readFile(InputStream inputStream) throws IOException {
		StringBuffer sb = new StringBuffer();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

		BufferedReader br = new BufferedReader(inputStreamReader);
		String strLine = null;

		while ((strLine = br.readLine()) != null) {
			sb.append(strLine);
		}

		inputStreamReader.close();

		return sb.toString();
	}
	
	public byte[] getBytes(InputStream inputStream) throws IOException {
		int bufferSize = inputStream.available();
		byte[] buffer = new byte[bufferSize];

		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream);

		int nextByte = 0;
		int index = 0;

		while ((nextByte = bufferedInputStream.read()) != -1) {
			buffer[index] = (byte) nextByte;
			index++;
		}

		releaseInputStream(bufferedInputStream);
		releaseInputStream(inputStream);

		return buffer;
	}

	public String getString(InputStream inputStream) throws IOException {
		int bufferSize = inputStream.available();
		byte[] buffer = new byte[bufferSize];

		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream);

		int nextByte = 0;
		int index = 0;

		while ((nextByte = bufferedInputStream.read()) != -1) {
			buffer[index] = (byte) nextByte;
			index++;
		}

		releaseInputStream(bufferedInputStream);
		releaseInputStream(inputStream);

		return new String(buffer);
	}

	protected void releaseInputStream(InputStream inputStream)
			throws IOException {
		if (inputStream != null) {
			inputStream.close();
		}
	}

	protected void releaseOutputStream(OutputStream outputStream)
			throws IOException {
		if (outputStream != null) {
			outputStream.flush();
			outputStream.close();
		}
	}

	protected static long getTotalMemoryOfPath(UnitOfMeasure unitOfMeasure,
			String absolutePath) {
		StatFs statFs = new StatFs(absolutePath);
		long blockSizeInBytes = CompatibilyStatFs.getCorrectBlockSize(statFs);
		long total = 0;
		if (UnitOfMeasure.KB == unitOfMeasure) {
			total = ((long) CompatibilyStatFs.getCorrectBlockCount(statFs)
					* blockSizeInBytes / 1024);
		} else if (UnitOfMeasure.MB == unitOfMeasure) {
			total = ((long) CompatibilyStatFs.getCorrectBlockCount(statFs)
					* blockSizeInBytes / 1048576);
		} else if (UnitOfMeasure.BYTES == unitOfMeasure) {
			total = ((long) CompatibilyStatFs.getCorrectBlockCount(statFs) * blockSizeInBytes);
		} else {
			total = ((long) CompatibilyStatFs.getCorrectBlockCount(statFs) * blockSizeInBytes);
		}
		return total;
	}

	protected static long getFreeMemoryOfPath(UnitOfMeasure unitOfMeasure,
			String absolutePath) {
		StatFs statFs = new StatFs(absolutePath);
		long freeSpace = 0;
		long blockSizeInBytes = CompatibilyStatFs.getCorrectBlockSize(statFs);
		if (UnitOfMeasure.KB == unitOfMeasure) {
			freeSpace = (CompatibilyStatFs.getCorrectAvailableBlocks(statFs) * blockSizeInBytes) / 1024;
		} else if (UnitOfMeasure.MB == unitOfMeasure) {
			freeSpace = (CompatibilyStatFs.getCorrectAvailableBlocks(statFs) * blockSizeInBytes) / 1048576;
		} else if (UnitOfMeasure.BYTES == unitOfMeasure) {
			freeSpace = (CompatibilyStatFs.getCorrectAvailableBlocks(statFs) * blockSizeInBytes);
		} else {
			freeSpace = (CompatibilyStatFs.getCorrectAvailableBlocks(statFs) * blockSizeInBytes);
		}
		return freeSpace;
	}

	protected static long getBusyMemoryOfPath(UnitOfMeasure unitOfMeasure,
			String absolutePath) { 
		long busyMemory = getTotalMemoryOfPath(unitOfMeasure, absolutePath)
				- getFreeMemoryOfPath(unitOfMeasure, absolutePath);
		return busyMemory;
	}
}
