package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.Administrator;
import com.fws.mvc.dao.AdministratorDao;

public class AdministratorDaoArc extends CommonDaoArc<Administrator> implements AdministratorDao {
	
	@Override
	public Boolean check(Connection connection, String name, String passwd) throws SQLException {
		String sql = "select count(*) from admin where name = ? and passWord = ? ;";
		Object[] params = {name, passwd};
		Long count = this.<Long>fetchScaler(connection, sql, params);
		return count == 1;
	}
	
}
