package com.ealib.db.utils;

import java.util.HashMap;
import java.util.Map;

public class AndroidPreparedStatement {

	protected StringBuffer querySql;
	protected Map<Integer, String> valueIndexes;
	private String compiledSqlString;
	protected int totalParams;

	public AndroidPreparedStatement(String sql) {
		this.querySql = new StringBuffer(sql);
		valueIndexes = new HashMap<Integer, String>();
	}

	public void setString(int i, String string) {
		string = "'" + string + "'";
		valueIndexes.put(i, string);
	}

	public void setInt(int i, int j) {
		valueIndexes.put(i, "" + j);
	}

	public StringBuffer getQuerySql() {
		return querySql;
	}

	public Map<Integer, String> getValueIndexes() {
		return valueIndexes;
	}

	public String getCompiledSqlString() {
		return compiledSqlString;
	}

	public void setCompiledSqlString(String compiledSqlString) {
		this.compiledSqlString = compiledSqlString;
	}

	public void compile() {
		totalParams = valueIndexes.size();
		String _tmpSql = querySql.toString();
		int _i = 0;
		while (_i < totalParams) {
			String s = valueIndexes.get(_i);
			if (s == null) {
				throw new IllegalArgumentException(
						"Error: The value in the position " + _i + " is null!");
			}

			_tmpSql = _tmpSql.replaceFirst("\\?", s);
			_i++;
		}
		compiledSqlString = _tmpSql;
	}

	@Override
	public String toString() {
		if (compiledSqlString == null)
			throw new SecurityException(
					"Can't call toString before the compile method!");
		return this.compiledSqlString;
	}

}
