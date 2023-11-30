package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface GlobalVarDao {
	void updateSysAnnoContext(Connection connection, String context) throws SQLException;
	String getSysAnnoContext(Connection connection) throws SQLException;
}
