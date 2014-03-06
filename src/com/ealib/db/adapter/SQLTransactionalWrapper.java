package com.ealib.db.adapter;

import android.database.SQLException;

public interface SQLTransactionalWrapper {

	void exec() throws SQLException, Exception;

}
