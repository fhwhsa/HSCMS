package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.Administrator;
import com.fws.mvc.bean.Guardian;
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
	public RegistrationRecord getRegistrationRecord(Connection connection, String emailAddr, String userType)
			throws SQLException {
		
		String sql = "select * from RAF where emailAddr = ? and userType = ?;";
		Object[] params = {emailAddr, userType};
		return fetch(connection, sql, params);
	}

	@Override
	public void deleteRegistrationRecord(Connection connection, String emailAddr, String userType)
			throws SQLException {
		
		String sql = "delete from RAF where emailAddr = ? and userType = ?;";
		Object[] params = {emailAddr, userType};
		update(connection, sql, params);
		
		// 发送邮箱信息告知注册被拒绝
		SendEmail.sendMail(emailAddr, "注册申请结果", "不通过");
	}

	@Override
	public void approvedRegistrationRecord(Connection connection, String emailAddr, String userType)
			throws SQLException {
		
		RegistrationRecord record = getRegistrationRecord(connection, emailAddr, userType);

		String sql = "delete from RAF where emailAddr = ? and userType = ?;";
		Object[] params = {emailAddr, userType};
		update(connection, sql, params);

		User user = null;
		if (record == null) return;
		if (record.getUserType().equals("Teacher"))
			user = new Teacher(record.getName(), record.getPassWord(), record.getEmailAddr(), record.getClassListToString());
		else
			user = new Guardian(record.getName(), record.getPassWord(), record.getEmailAddr(), record.getChildListToString(), record.getClassListToString());
		UserDaoArc userDaoArc = new UserDaoArc();
		userDaoArc.add(connection, user, record.getUserType());
		
		// 发送邮箱信息告知注册成功
		SendEmail.sendMail(emailAddr, "注册申请结果", "通过");
	}

}
