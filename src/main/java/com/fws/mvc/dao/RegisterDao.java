package com.fws.mvc.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.Guardian;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.bean.Teacher;

public interface RegisterDao {
	public Boolean isEAddrExist(Connection connection, String userType, String emailAddr) throws SQLException;
	public void addApplication(Connection connection, RegistrationRecord record) throws SQLException;
}
