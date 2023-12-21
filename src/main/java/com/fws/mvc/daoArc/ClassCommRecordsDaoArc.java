package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.ClassCommRecords;
import com.fws.mvc.dao.ClassCommRecordsDao;

public class ClassCommRecordsDaoArc extends CommonDaoArc<ClassCommRecords> implements ClassCommRecordsDao {

	@Override
	public void addRecord(Connection connection, ClassCommRecords classCommRecords) throws SQLException {
		String sql = "insert into class_communication_records (classNo, senderName, senderEmailAddr, context, sendingDate) values (?, ?, ?, ?, ?);";
		Object[] params = {classCommRecords.getClassNo(), classCommRecords.getSenderName(), 
				classCommRecords.getSenderEmailAddr(), classCommRecords.getContext(), classCommRecords.getSendingDate()};
		update(connection, sql, params);
	}

	@Override
	public List<ClassCommRecords> getAllRecords(Connection connection, String classNo) throws SQLException {
		String sql = "select * from class_communication_records where classNo = ?";
		Object[] params = {classNo};
		return fetchList(connection, sql, params);
	}

	@Override
	public List<ClassCommRecords> getAllRecordsByFilter(Connection connection, String classNo, String filterContext,
			String filterDate) throws SQLException {
		String sql = "select * from class_communication_records where classNo = ? and context like ? and sendingDate like ?;";
		Object[] params = {classNo, filterContext, filterDate};
		return fetchList(connection, sql, params);
	}

}
