package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.User;
import com.fws.mvc.dao.UserDao;

public class UserDaoArc extends CommonDaoArc<User> implements UserDao {

	public void add(Connection connection, User user) throws SQLException {
		String sql = "insert into user (name, passWord, emailAddr, userType) values (?, ?, ?, ?);";
		Object[] params = {user.getName(), user.getPassWord(), user.getEmailAddr(), user.getUserType()};
		update(connection, sql, params);
	}
	
	@Override
	public Boolean isExist(Connection connection, String emailAddr) throws SQLException {
		String sql = "select count(*) from user where emailAddr = ?";
		Object[] params = {emailAddr};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}

	@Override
	public User getUser(Connection connection, String emailAddr, String userType) throws SQLException {
		String sql = "select * from user where emailAddr = ? and userType = ?;";
		Object[] params = {emailAddr, userType};
		User u = fetch(connection, sql, params);
		return u;
	}

	@Override
	public String getUserName(Connection connection, String emailAddr, String userType) throws SQLException {
		String sql = "select name from user where emailAddr = ? and userType = ?;";
		Object[] params = {emailAddr, userType};
		String res = this.<String>fetchScaler(connection, sql, params);
		return res;
	}
	
	public void updatePasswd(Connection connection, String emailAddr, String userType, String newPasswd) throws SQLException {
		String sql = "update user set passWord = ? where emailAddr = ? and userType = ?;";		
//		System.out.println(sql + " " + newPasswd + " " + emailAddr + " " + userType);
		Object[] params = {newPasswd, emailAddr, userType};
		update(connection, sql, params);
	}

	@Override
	public Boolean checkPasswd(Connection connection, String emailAddr, String passWord, String userType) throws SQLException {
		String sql = "select count(*) from user where emailAddr = ? and userType = ? and passWord = ?;";
		Object[] params = {emailAddr, userType, passWord};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}
}
