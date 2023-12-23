package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface GlobalVarDao {
	
	// 更新系统公告
	void updateSysAnnoContext(Connection connection, String context) throws SQLException;
	
	// 获取系统公告
	String getSysAnnoContext(Connection connection) throws SQLException;
}
