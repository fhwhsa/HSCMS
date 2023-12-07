package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.bean.Teacher;
import com.fws.mvc.bean.User;
import com.fws.mvc.dao.AdministratorDao;
import com.fws.mvc.utils.SendEmail;

public class AdministratorDaoArc extends CommonDaoArc<RegistrationRecord> implements AdministratorDao {

	@Override
	public List<RegistrationRecord> getRegistrationRecordsList(Connection connection) throws SQLException {
		String sql = "select * from RAF;";
		List<RegistrationRecord> records = fetchList(connection, sql, null);
		return records;
	}

	@Override
	public RegistrationRecord getRegistrationRecord(Connection connection, String emailAddr)
			throws SQLException {
		
		String sql = "select * from RAF where emailAddr = ?;";
		Object[] params = {emailAddr};
		return fetch(connection, sql, params);
	}

	@Override
	public void deleteRegistrationRecord(Connection connection, String emailAddr)
			throws SQLException {
		
		String sql = "delete from RAF where emailAddr = ?;";
		Object[] params = {emailAddr};
		update(connection, sql, params);
		
		// 发送邮箱信息告知注册被拒绝
		SendEmail.sendMail(emailAddr, "注册申请结果", "不通过");
	}

	@Override
	public void approvedRegistrationRecord(Connection connection, String emailAddr)
			throws SQLException {
		
		RegistrationRecord record = getRegistrationRecord(connection, emailAddr);

		String sql = "delete from RAF where emailAddr = ?;";
		Object[] params = {emailAddr};
		update(connection, sql, params);

		if (record == null) return;
		User user = new Teacher(record.getName(), record.getPassWord(), record.getEmailAddr());
		UserDaoArc userDaoArc = new UserDaoArc();
		userDaoArc.add(connection, user, "Teacher");
		
		// 发送邮箱信息告知注册成功
		SendEmail.sendMail(emailAddr, "注册申请结果", "通过");
	}

}
