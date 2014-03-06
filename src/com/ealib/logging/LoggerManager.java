package com.ealib.logging;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.ealib.io.InternalFileManager;
import com.ealib.io.SDFileManager;
import com.ealib.io.sd.ExternalStorageNotAvailableException;
import com.ealib.io.sd.ExternalStorageNotWriteable;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import android.app.Application;
import android.content.Context;

public final class LoggerManager {

	public interface OnLoggerInitializeHandler {
		void customizeConfiguration(LogConfigurator logconfigurator);
	}

	public enum LoggingFileDestination {
		PRIVATE_APP_FILE, SD_FILE
	}

	public static class LoggingConfiguration {
		private String filename;
		private LoggingFileDestination loggingFileDestination;

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		public LoggingFileDestination getLoggingFileDestination() {
			return loggingFileDestination;
		}

		public void setLoggingFileDestination(
				LoggingFileDestination loggingFileDestination) {
			this.loggingFileDestination = loggingFileDestination;
		}

	}

	public final static LoggingConfiguration loggingConfiguration = new LoggingConfiguration();
	protected static final LogConfigurator logConfigurator = new LogConfigurator();
	protected static boolean initialized = false;
	
	public static final Object SYNCH = new Object();
	
	public static <mApplication extends Application> void initialize(
			mApplication application, LoggingFileDestination destination,
			String filename, Level rootLogLevel) {

		if (!initialized) {
			setCorrectFilename(destination, application, filename);
			logConfigurator.setRootLevel(rootLogLevel);
			logConfigurator.setLevel("org.apache", Level.ERROR);
			logConfigurator.configure();
		}
	}

	public synchronized static <mApplication extends Application> void initialize(
			mApplication application, LoggingFileDestination destination,
			String filename, OnLoggerInitializeHandler onLoggerInitializeHandler) {

		if (!initialized) {
			setCorrectFilename(destination, application, filename);

			onLoggerInitializeHandler.customizeConfiguration(logConfigurator);

			logConfigurator.configure();
			initialized = true;
		}

	}

	private static <mApplication extends Application> void setCorrectFilename(
			LoggingFileDestination destination, mApplication application,
			String filename) {

		String absoluteFilePath = null;
		Context applicationContext = application.getApplicationContext();

		class SDController {
			public boolean canWriteToSD() {
				try {
					SDFileManager.checkExternalMedia();
					return true;
				} catch (ExternalStorageNotWriteable e) {
					return false;
				} catch (ExternalStorageNotAvailableException e) {
					return false;
				}
			}
		}

		if (LoggingFileDestination.SD_FILE.equals(destination)) {
			if (new SDController().canWriteToSD()) {
				absoluteFilePath = SDFileManager.getSDAbsolutePath()
						+ File.separator + filename;
				loggingConfiguration
						.setLoggingFileDestination(LoggingFileDestination.SD_FILE);
			}
			// Cannot write in SD Using Internal App Storage
			else {
				String internalAppAbsoluteFilePath = new InternalFileManager(
						applicationContext).getFileAbsolutePath(filename);
				absoluteFilePath = internalAppAbsoluteFilePath;
				loggingConfiguration
						.setLoggingFileDestination(LoggingFileDestination.PRIVATE_APP_FILE);
			}
		}
		// Internal App Log
		else if (LoggingFileDestination.PRIVATE_APP_FILE.equals(destination)) {
			String internalAppAbsoluteFilePath = new InternalFileManager(
					applicationContext).getFileAbsolutePath(filename);
			absoluteFilePath = internalAppAbsoluteFilePath;
			loggingConfiguration
					.setLoggingFileDestination(LoggingFileDestination.PRIVATE_APP_FILE);
		} else {
			throw new IllegalArgumentException(
					LoggingFileDestination.class.getCanonicalName()
							+ " with value " + destination
							+ " is not a valid argument.");
		}

		loggingConfiguration.setFilename(filename);
		// Log4j uses only absolute path
		logConfigurator.setFileName(absoluteFilePath);
	}

	public static <C extends Context> Logger getLogger(C context) {
		return Logger.getLogger(context.getClass());
	}

	public static <C extends Context> Logger getLogger(String loggerName) {
		return Logger.getLogger(loggerName);
	}

	public synchronized static boolean isInitialized() {
		return initialized;
	}

	public static String read(Context context) throws IOException {
		String filename = loggingConfiguration.getFilename();
		LoggingFileDestination fileDestination = loggingConfiguration
				.getLoggingFileDestination();

		if (LoggingFileDestination.PRIVATE_APP_FILE.equals(fileDestination)) {
			String readFile = new InternalFileManager(context)
					.readFile(filename);
			return readFile;
		} else if (LoggingFileDestination.SD_FILE.equals(fileDestination)) {
			String readFile = SDFileManager.read(filename);
			return readFile;
		}
		return null;
	}

	public static boolean cleanLogFile(Context context) throws IOException {
		String filename = loggingConfiguration.getFilename();
		LoggingFileDestination fileDestination = loggingConfiguration
				.getLoggingFileDestination();

		if (LoggingFileDestination.PRIVATE_APP_FILE.equals(fileDestination)) {
			InternalFileManager internalFileManager = new InternalFileManager(
					context);
			boolean exists = internalFileManager.existsFile(filename);
			if (exists) {
				internalFileManager.delete(filename);
				internalFileManager.createFile(filename);
				return true;
			}
			return false;

		} else if (LoggingFileDestination.SD_FILE.equals(fileDestination)) {
			if (SDFileManager.existsFile(filename)) {
				SDFileManager.delete(filename);
				SDFileManager.createFile(filename);
				return true;
			}
			return false;
		} else {
			return false;
		}
	}

	public static String shareIntoSDLogFile(Context context) throws IOException {
		String filename = loggingConfiguration.getFilename();
		String readContent = read(context);
		SDFileManager.writeToSDFile(filename, readContent, true);
		return filename;
	}

}
