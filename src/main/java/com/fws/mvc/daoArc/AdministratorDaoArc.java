package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.Administrator;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.dao.AdministratorDao;

public class AdministratorDaoArc extends CommonDaoArc<RegistrationRecord> implements AdministratorDao {
	
	@Override
	public Boolean isExist(Connection connection, String name, String passwd) throws SQLException {
		String sql = "select count(*) from admin where name = ? and passWord = ? ;";
		Object[] params = {name, passwd};
		Long count = this.<Long>fetchScaler(connection, sql, params);
		return count == 1;
	}

	@Override
	public List<RegistrationRecord> getRegistrationRecordsList(Connection connection) throws SQLException {
		String sql = "select * from RAF;";
		List<RegistrationRecord> records = fetchList(connection, sql, null);
		return records;
	}

	@Override
	public RegistrationRecord getRegistrationRecord(Connection connection, String emailAddr, String userType)
			throws SQLException {
		
		String sql = "select * from RAF where emailAddr = ? and userType = ?;";
		Object[] params = {emailAddr, userType};
		return this.<RegistrationRecord>fetchScaler(connection, sql, params);
	}

	@Override
	public void deleteRegistrationRecord(Connection connection, String emailAddr, String userType)
			throws SQLException {
		
		String sql = "delete from RAF where emailAddr = ? and userType = ?;";
		Object[] params = {emailAddr, userType};
		update(connection, sql, params);
	}

	@Override
	public void approvedRegistrationRecord(Connection connection, String emailAddr, String userType)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
