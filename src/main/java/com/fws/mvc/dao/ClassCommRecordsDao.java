package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.ClassCommRecords;

public interface ClassCommRecordsDao {

	// 添加一条记录
	public void addRecord(Connection connection, ClassCommRecords classCommRecords) throws SQLException;
	
	// 获取指定班级的所有记录（时间降序）
	public List<ClassCommRecords> getAllRecords(Connection connection, String classNo) throws SQLException;
	
	// 获取指定班级筛选后的记录（时间降序）
	public List<ClassCommRecords> getAllRecordsByFilter(Connection connection, String classNo, String filterContext, String filterDate) throws SQLException;
}
