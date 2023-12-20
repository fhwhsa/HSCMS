package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.UserClassMap;
import com.fws.mvc.dao.UserClassMapDao;

public class UserClassMapDaoArc extends CommonDaoArc<UserClassMap> implements UserClassMapDao {

	@Override
	public List<UserClassMap> getClassMembers(Connection connection, String classNo) throws SQLException {
		String sql = "select u.name, u.emailAddr, ucm.classNo from user_class_map ucm inner join user u on ucm.emailAddr = u.emailAddr where ucm.classNo = ?;";
		Object[] params = {classNo};
		return fetchList(connection, sql, params);
	}

	@Override
	public UserClassMap getClassCreter(Connection connection, String classNo) throws SQLException {
		String sql = "select u.name, u.emailAddr, c.classNo from classinfo c inner join user u on c.createrEmailAddr = u.emailAddr where c.classNo = ?;";
		Object[] params = {classNo};
		return fetch(connection, sql, params);
	}


}
