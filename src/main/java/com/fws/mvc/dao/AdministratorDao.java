package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface AdministratorDao {
	public Boolean check(Connection connection, String name, String passwd) throws SQLException;
}
