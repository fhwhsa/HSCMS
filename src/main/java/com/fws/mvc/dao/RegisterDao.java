package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.RegistrationRecord;

public interface RegisterDao {
	
	// 检查邮箱是否被使用
	public Boolean isEAddrExist(Connection connection, String emailAddr) throws SQLException;
	
	// 添加老师注册申请记录
	public void addApplication(Connection connection, RegistrationRecord record) throws SQLException;
	
	// 获取所有申请记录
	public List<RegistrationRecord> getRegistrationRecordsList(Connection connection) throws SQLException;
	
	// 获取单个申请记录
	public RegistrationRecord getRegistrationRecord(Connection connection, String emailAddr) throws SQLException;
	
	// 删除单个申请记录
	public void deleteRegistrationRecord(Connection connection, String emailAddr) throws SQLException;
	
	// 通过单个申请记录
	public void approvedRegistrationRecord(Connection connection, String emailAddr) throws SQLException;
}
