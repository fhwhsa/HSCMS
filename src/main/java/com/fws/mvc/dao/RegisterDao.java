package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.RegistrationRecord;

public interface RegisterDao {
	public Boolean isEAddrExist(Connection connection, String emailAddr) throws SQLException;
	
	public void addApplication(Connection connection, RegistrationRecord record) throws SQLException;
	
	public List<RegistrationRecord> getRegistrationRecordsList(Connection connection) throws SQLException;
	
	public RegistrationRecord getRegistrationRecord(Connection connection, String emailAddr) throws SQLException;
	
	public void deleteRegistrationRecord(Connection connection, String emailAddr) throws SQLException;
	
	public void approvedRegistrationRecord(Connection connection, String emailAddr) throws SQLException;
}
