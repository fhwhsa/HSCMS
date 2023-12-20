package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.ClassAnnoMap;

public interface ClassAnnoMapDao {

	// 添加通知
	public void addAnno(Connection connection, String classNo, String context) throws SQLException;
	
	// 获取指定班级所有通知（降序）
	public List<ClassAnnoMap> getAnnoList(Connection connection, String classNo) throws SQLException;
}
