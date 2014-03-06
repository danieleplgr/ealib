package com.ealib.utils.path;

import java.io.File;

public class PathUtils {

	public static String composePath(String... dirs) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < dirs.length; i++) {
			stringBuilder.append(dirs[i]);
			if (i != (dirs.length-1)) {
				stringBuilder.append(File.separatorChar);
			}
		}
		return stringBuilder.toString();
	}

	public static String packageToDirectory(String packageString) {
		return packageString.replace('.', File.separatorChar);
	}
	
}
