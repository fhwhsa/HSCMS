package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.User;

public interface UserDao {

	// 添加用户
	public void add(Connection connection, User user) throws SQLException;

	// 检查邮箱是否存在
	public Boolean isExist(Connection connection, String emailAddr) throws SQLException;
	
	// 检查密码
	public Boolean checkPasswd(Connection connection, String emailAddr, String passWord, String userType) throws SQLException;
	
	// 获取用户对象
	public User getUser(Connection connection, String emailAddr, String userType) throws SQLException;
	
	// 获取用户姓名
	public String getUserName(Connection connection, String emailAddr, String userType) throws SQLException;

	// 修改密码
	public void updatePasswd(Connection connection, String emailAddr, String userType, String newPasswd) throws SQLException;
}
