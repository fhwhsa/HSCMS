package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.Guardian;
import com.fws.mvc.bean.Teacher;
import com.fws.mvc.bean.User;
import com.fws.mvc.dao.UserDao;

public class UserDaoArc extends CommonDaoArc<User> implements UserDao {

	public void add(Connection connection, User user, String userType) throws SQLException {
		if (userType.equals("Teacher")) {
			Teacher obj = (Teacher)user;
			String sql = "insert into teacher (name, passWord, emailAddr, classList) values (?, ?, ?, ?);";
			Object[] params = {obj.getName(), obj.getPassWord(), obj.getEmailAddr(), obj.getClassListToString()};
			update(connection, sql, params);
		}
		else {
			Guardian obj = (Guardian)user;
			String sql = "insert into guardian (name, passWord, emailAddr, childList, classList) values (?, ?, ?, ?, ?);";
			Object[] params = {obj.getName(), obj.getPassWord(), obj.getEmailAddr(), obj.getChildListToString(), obj.getClassListToString()};
			update(connection, sql, params);
		}
	}
	
	@Override
	public Boolean isExist(Connection connection, String emailAddr, String passWord, String userType) throws SQLException {
		String sql = "select count(*) from " + userType + " where emailAddr = ? and passWord = ?;";
		Object[] params = {emailAddr, passWord};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}

	@Override
	public User get(Connection connection, String emailAddr, String userType) throws SQLException {
		String sql = "select * from " + userType + " where emailAddr = ?;";
		Object[] params = {emailAddr};
		User u = fetch(connection, sql, params);
		return u;
	}
	
	
}
