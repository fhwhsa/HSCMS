package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.User;

public interface UserDao {

	public void add(Connection connection, User user, String userType) throws SQLException;

	public Boolean isExist(Connection connection, String emailAddr, String passWord, String userType) throws SQLException;
	
	public User get(Connection connection, String emailAddr, String userType) throws SQLException;
}
