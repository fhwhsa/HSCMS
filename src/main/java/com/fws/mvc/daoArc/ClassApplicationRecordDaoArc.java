package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.ClassApplicationRecord;
import com.fws.mvc.dao.ClassApplicationRecordDao;

public class ClassApplicationRecordDaoArc extends CommonDaoArc<ClassApplicationRecord> implements ClassApplicationRecordDao {

	@Override
	public void addApplicationRecord(Connection connection, ClassApplicationRecord record) throws SQLException {
		String sql = "insert into jcaf (classNo, emailAddr, name) values (?, ?, ?);";
		Object[] params = {record.getClassNo(), record.getEmailAddr(), record.getName()};
		update(connection, sql, params);
	}

	@Override
	public List<ClassApplicationRecord> getApplicationRecordsList(Connection connection, String classNo)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hasSubmit(Connection connection, ClassApplicationRecord record) throws SQLException {
		String sql = "select count(*) from jcaf where emailAddr = ? and classNo = ?;";
		Object[] params = {record.getEmailAddr(), record.getClassNo()};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}

}
