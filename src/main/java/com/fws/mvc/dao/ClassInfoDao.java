package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.ClassInfo;

// 关联表caf和classinfo
public interface ClassInfoDao {
	
	public Boolean findByNo(Connection connection, String no) throws SQLException;
	
	public Boolean findByName(Connection connection, String name) throws SQLException;
	
	// 添加班级
	public void add(Connection connection, ClassInfo classInfo) throws SQLException;
	
	// 提交班级申请
	public void addApplication(Connection connection, ClassInfo classInfo) throws SQLException;

	// 获取所有班级申请记录
	public List<ClassInfo> getClassInfoRecordsListFromApplication(Connection connection) throws SQLException;
	
	// 从申请记录中获取班级对象
	public ClassInfo getClassInfoRecordFromApplication(Connection connection, String no) throws SQLException;

	// 从申请记录中删除班级对象
	public void deleteClassInfoRecordFromApplication(Connection connection, String no) throws SQLException;
	
	// 同意班级创建申请
	public void approvedApplication(Connection connection, String no) throws SQLException;
	
	// 拒绝班级创建申请
	public void refusedApplication(Connection connection, String no) throws SQLException;

	// 获取创建的班级列表
	public List<ClassInfo> getCreateClassRecordsList(Connection connection, String emailAddr) throws SQLException;
}
