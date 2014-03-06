package com.ealib.db.dao;

import com.ealib.db.SQLiteDatabaseManager;

@Deprecated
public abstract class LocalDao {

	protected SQLiteDatabaseManager databaseManager;

	public LocalDao(SQLiteDatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	
	protected static String quote(String value){
		StringBuffer sbBuffer = new StringBuffer(value);
		sbBuffer.insert(0, "'");
		sbBuffer.append("'");
		return sbBuffer.toString();
	}

	protected static String escapeSpecialChars(String value){
		String replaceAll = value.replace("'", "''");
		return replaceAll;
	}
	
}
