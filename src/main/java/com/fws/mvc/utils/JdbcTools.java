package com.fws.mvc.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcTools {
	
	private static ComboPooledDataSource cpds = null;

	public static Connection getConnectionByPools() throws Exception {
		if (cpds == null)
			cpds = new ComboPooledDataSource("intergalactoApp");
		return cpds.getConnection();
	}

	public static void releaseSources(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
