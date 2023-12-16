package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.User;

public interface UserDao {

	public void add(Connection connection, User user) throws SQLException;

	public Boolean isExist(Connection connection, String emailAddr) throws SQLException;
	
	public Boolean checkPasswd(Connection connection, String emailAddr, String passWord, String userType) throws SQLException;
	
	public User getUser(Connection connection, String emailAddr, String userType) throws SQLException;
	
	public String getUserName(Connection connection, String emailAddr, String userType) throws SQLException;

	public void updatePasswd(Connection connection, String emailAddr, String userType, String newPasswd) throws SQLException;
}
