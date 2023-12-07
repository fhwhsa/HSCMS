package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.GlobalVar;
import com.fws.mvc.bean.Guardian;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.bean.Teacher;
import com.fws.mvc.dao.RegisterDao;

public class RegisterDaoArc extends CommonDaoArc<RegistrationRecord> implements RegisterDao {

	@Override
	public Boolean isEAddrExist(Connection connection, String userType, String emailAddr) throws SQLException {
		if (userType.equals("Teacher")) {			
			String sql = "select count(*) from Teacher where emailAddr = ?;"; 
			Object[] params = {emailAddr};
			Long t = this.<Long>fetchScaler(connection, sql, params);
			if (t == 1) return true;
			sql = "select count(*) from raf where emailAddr = ?;";
			Object[] params2 = {emailAddr, userType};
			t = this.<Long>fetchScaler(connection, sql, params2);
			return t == 1;
		}
		else {
			String sql = "select count(*) from Guardian where emailAddr = ?;"; 
			Object[] params = {emailAddr};
			Long t = this.<Long>fetchScaler(connection, sql, params);
			return t == 1;
		}
	}

	@Override
	public void addApplication(Connection connection, RegistrationRecord record) throws SQLException {
		System.out.println(record.toString());
		String sql = "insert into RAF (name, passWord, emailAddr) values (?, ?, ?);";
		System.out.println();
		Object[] params = {record.getName(), record.getPassWord(), record.getEmailAddr()};
		update(connection, sql, params);
	}

}
