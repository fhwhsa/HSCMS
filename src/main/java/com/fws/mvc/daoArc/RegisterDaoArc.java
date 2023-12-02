package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.GlobalVar;
import com.fws.mvc.bean.Guardian;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.bean.Teacher;
import com.fws.mvc.dao.RegisterDao;

public class RegisterDaoArc extends CommonDaoArc<GlobalVar> implements RegisterDao {

	@Override
	public Boolean isEAddrExist(Connection connection, String userType, String emailAddr) throws SQLException {
		String sql = "select count(*) from " + userType + " where emailAddr = ?;"; 
		Object[] params = {emailAddr};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}

	@Override
	public void addApplication(Connection connection, RegistrationRecord record) throws SQLException {
		String sql = "insert into RAF (name, passWord, emailAddr, childList) values (?, ?, ?, ?);";
		Object[] params = {record.getName(), record.getPassWord(), record.getEmailAddr(), record.getChildList()};
		update(connection, sql, params);
	}

}
