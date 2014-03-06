package com.ealib.db;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.ealib.db.adapter.CursorCallbackExecuter;
import com.ealib.db.adapter.SQLTransactionalWrapper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

@Deprecated
public abstract class SQLiteDatabaseManager {

	private static final boolean LOCK_ENABLE = false;
	private static SQLiteDatabase sqliteDatabase;

	protected Context context;
	//protected SQLiteOpenHelper helper;

	protected String databaseName;
	private String installDatabaseSql;

	protected abstract String getDatabaseName();

	public SQLiteDatabaseManager(Context context, String istallSqlString) {
		this.context = context;
		this.installDatabaseSql = istallSqlString;
		this.databaseName = getDatabaseName();
		if (sqliteDatabase == null) {
			open(LOCK_ENABLE, context, databaseName);
		}
	}

	public void install() throws IOException {
		openIfClosed();
		if (!hasBeenCratedTables()) {
			StringBuffer _sql = new StringBuffer(this.installDatabaseSql);
			String _sqlString = _sql.toString();
			String[] split = _sqlString.split(";");
			List<String> list = Arrays.asList(split);
			executeCommandsList(list);
		}
	}

	protected abstract boolean hasBeenCratedTables() throws IOException;

	public static void open(boolean lock, Context context, String databaseName) {
		sqliteDatabase = context.openOrCreateDatabase(databaseName,
				SQLiteDatabase.CREATE_IF_NECESSARY, null);
		sqliteDatabase.setVersion(1);
		sqliteDatabase.setLocale(Locale.getDefault());
		sqliteDatabase.setLockingEnabled(lock);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}

	public void close() {
		if (sqliteDatabase != null) {
			if (sqliteDatabase.isOpen()) {
				sqliteDatabase.close();
				sqliteDatabase = null;
			}
		}
	}

	public void executeCommand(String sql) {
		openIfClosed();
		sqliteDatabase.execSQL(sql);
	}

	public void executeCommandsList(List<String> commands) {
		for (String sqlCommand : commands) {
			this.executeCommand(sqlCommand);
		}
	}

	public void executeQuery(String sql,
			CursorCallbackExecuter cursorCallbackExecuter) {
		openIfClosed();

		Cursor c = sqliteDatabase.rawQuery(sql, null);
		cursorCallbackExecuter.wrapOnCursor(c);
		closeCursor(c);
	}

	public void executeSelectList(String sql,
			CursorCallbackExecuter cursorCallbackExecuter) {
		openIfClosed();

		Cursor c = sqliteDatabase.rawQuery(sql, null);

		int count = c.getCount();
		c.moveToFirst();
		for (int i = 0; i < count; i++) {

			cursorCallbackExecuter.wrapOnCursor(c);

			if (!c.isLast())
				c.moveToNext();
		}

		closeCursor(c);

	}

	protected void closeCursor(Cursor c) {
		if (c != null){
			if(!c.isClosed()){
				c.close();
			}
		}
	}

	public void transaction(SQLTransactionalWrapper transactionalWrapper)
			throws Exception {
		openIfClosed();

		sqliteDatabase.beginTransaction();
		try {
			transactionalWrapper.exec();
			sqliteDatabase.setTransactionSuccessful();
		} catch (SQLException exc) {
			throw exc;
		} catch (Exception e) {
			throw e;
		} finally {
			if (sqliteDatabase.inTransaction())
				sqliteDatabase.endTransaction();
		}
	}

	protected void openIfClosed() {
		if (sqliteDatabase == null) {
			open(LOCK_ENABLE, context, databaseName);
			return;
		}
		if (!sqliteDatabase.isOpen()){
			open(LOCK_ENABLE, context, databaseName);
			return;
		}
	}

}
