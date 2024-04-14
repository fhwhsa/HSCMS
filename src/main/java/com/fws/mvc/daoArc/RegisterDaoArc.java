package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.GlobalVar;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.bean.User;
import com.fws.mvc.dao.RegisterDao;
import com.fws.mvc.utils.SendEmail;

public class RegisterDaoArc extends CommonDaoArc<RegistrationRecord> implements RegisterDao {

	@Override
	public List<RegistrationRecord> getRegistrationRecordsList(Connection connection) throws SQLException {
		String sql = "select * from raf;";
		return fetchList(connection, sql, null);
	}

	@Override
	public RegistrationRecord getRegistrationRecord(Connection connection, String emailAddr)
			throws SQLException {
		
		String sql = "select * from raf where emailAddr = ?;";
		Object[] params = {emailAddr};
		return fetch(connection, sql, params);
	}

	@Override
	public void deleteRegistrationRecord(Connection connection, String emailAddr)
			throws SQLException {
		
		String sql = "delete from raf where emailAddr = ?;";
		Object[] params = {emailAddr};
		update(connection, sql, params);
	}

	@Override
	public void approvedRegistrationRecord(Connection connection, String emailAddr)
			throws SQLException {
		
		RegistrationRecord record = getRegistrationRecord(connection, emailAddr);

		String sql = "delete from raf where emailAddr = ?;";
		Object[] params = {emailAddr};
		update(connection, sql, params);

		if (record == null) return;
		User user = new User(record.getName(), record.getPassWord(), record.getEmailAddr(), "Teacher");
		UserDaoArc userDaoArc = new UserDaoArc();
		userDaoArc.add(connection, user);
		
	}
	
	@Override
	public Boolean isEAddrExist(Connection connection, String emailAddr) throws SQLException {
		String sql = "select count(*) from raf where emailAddr = ?;";
		Object[] params2 = {emailAddr};
	 	Long t = this.<Long>fetchScaler(connection, sql, params2);
		return t == 1;
	}

	@Override
	public void addApplication(Connection connection, RegistrationRecord record) throws SQLException {
		System.out.println(record.toString());
		String sql = "insert into raf (name, passWord, emailAddr) values (?, ?, ?);";
		System.out.println();
		Object[] params = {record.getName(), record.getPassWord(), record.getEmailAddr()};
		update(connection, sql, params);
	}

}
