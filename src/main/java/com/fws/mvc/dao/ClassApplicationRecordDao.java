package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.ClassApplicationRecord;

public interface ClassApplicationRecordDao {
	
	// 检查是否已经提交
	public Boolean hasSubmit(Connection connection, ClassApplicationRecord record) throws SQLException;

	// 添加加入班级申请
	public void addApplicationRecord(Connection connection, ClassApplicationRecord record) throws SQLException;
	
	// 将班级申请加入到用户班级映射
	public void addUserClassMap(Connection connection, ClassApplicationRecord record) throws SQLException;
	
	// 删除加入班级申请
	public void deleteRecord(Connection connection, ClassApplicationRecord record) throws SQLException;
	
	// 获取指定班级的加入申请
	public List<ClassApplicationRecord> getApplicationRecordsList(Connection connection, String classNo) throws SQLException;
}
