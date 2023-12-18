package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ChildDao {

	// 添加家长-孩子映射
	public void addGuardianChildMap(Connection connection, String emailAddr, String childName) throws SQLException;
	
}
