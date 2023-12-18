package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.dao.ChildDao;

public class ChildDaoArc extends CommonDaoArc<GlobalVarDaoArc> implements ChildDao {

	@Override
	public void addGuardianChildMap(Connection connection, String emailAddr, String childName) throws SQLException {
		String sql = "insert into guardian_child_map (emailAddr, childName) values (?, ?);";
		Object[] params = {emailAddr, childName};
		update(connection, sql, params);
	}

}
