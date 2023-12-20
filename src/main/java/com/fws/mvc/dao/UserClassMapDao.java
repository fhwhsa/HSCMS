package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.UserClassMap;

public interface UserClassMapDao {

	// 获取班级成员姓名
	public List<UserClassMap> getClassMembers(Connection connection, String classNo) throws SQLException;
	
	// 获取班级创建者姓名
	public UserClassMap getClassCreter(Connection connection, String classNo) throws SQLException;
	
	
}
