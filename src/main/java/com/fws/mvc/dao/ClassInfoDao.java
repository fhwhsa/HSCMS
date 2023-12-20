package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.ClassInfo;

// 关联表caf和classinfo
public interface ClassInfoDao {
	
	// caf, classinfo 查已审核或申请创建的班级
	public Boolean findByNo(Connection connection, String no) throws SQLException;
	
	// caf, classinfo 查已审核或申请创建的班级
	public Boolean findByName(Connection connection, String name) throws SQLException;
	
	// caf 查审核通过的班级
	public Boolean isExist(Connection connection, String classNo) throws SQLException;
	
	// 添加班级(classinfo)
	public void add(Connection connection, ClassInfo classInfo) throws SQLException;
	
	// 提交班级申请(caf)
	public void addApplication(Connection connection, ClassInfo classInfo) throws SQLException;

	// 获取所有班级申请记录(caf)
	public List<ClassInfo> getClassInfoRecordsListFromApplication(Connection connection) throws SQLException;
	
	// 从申请记录中获取班级对象(caf)
	public ClassInfo getClassInfoRecordFromApplication(Connection connection, String no) throws SQLException;

	// 从申请记录中删除班级对象(caf)
	public void deleteClassInfoRecordFromApplication(Connection connection, String no) throws SQLException;
	
	// 同意班级创建申请(caf,classinfo)
	public void approvedApplication(Connection connection, String no) throws SQLException;
	
	// 拒绝班级创建申请(caf)
	public void refusedApplication(Connection connection, String no) throws SQLException;

	// 获取创建的班级列表(classinfo)
	public List<ClassInfo> getCreateClassRecordsList(Connection connection, String emailAddr) throws SQLException;
	
	// 获取加入的班级列表(classinfo, user_class_map)
	public List<ClassInfo> getJoinedClassRecordsList(Connection connection, String emString) throws SQLException;
	
	// 获取指定班级信息(classinfo)
	public ClassInfo getClassInfo(Connection connection, String classNo) throws SQLException;
}
