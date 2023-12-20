package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import org.apache.naming.factory.SendMailFactory;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.dao.ClassInfoDao;
import com.fws.mvc.utils.SendEmail;

public class ClassInfoDaoArc extends CommonDaoArc<ClassInfo> implements ClassInfoDao {

	@Override
	public Boolean findByNo(Connection connection, String no) throws SQLException {
		String sql = "SELECT COUNT(*) FROM (SELECT * from caf WHERE classNo = ? UNION ALL select * from classinfo WHERE classNo = ?) as t;";
		Object[] params = {no, no};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}

	@Override
	public Boolean findByName(Connection connection, String name) throws SQLException {
		String sql = "SELECT COUNT(*) FROM (SELECT * from caf WHERE className = ? UNION ALL select * from classinfo WHERE className = ?) as t;";
		Object[] params = {name, name};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}
	
	@Override
	public void add(Connection connection, ClassInfo classInfo) throws SQLException {
		String sql = "insert into classinfo (classNo, className, creater, createTimeStamp, createrEmailAddr) values (?, ?, ?, ?, ?);";
		Object[] params = {classInfo.getClassNo(), classInfo.getClassName(), classInfo.getCreater(), classInfo.getCreateTimeStamp(), classInfo.getCreaterEmailAddr()};
		update(connection, sql, params);
	}

	@Override
	public void addApplication(Connection connection, ClassInfo classInfo) throws SQLException {
		String sql = "insert into caf (classNo, className, creater, createTimeStamp, createrEmailAddr) values (?, ?, ?, ?, ?);";		
		Object[] params = {classInfo.getClassNo() ,classInfo.getClassName(), classInfo.getCreater(), classInfo.getCreateTimeStamp(), classInfo.getCreaterEmailAddr()};
		
		CommonDaoArc<String> tmpCDA = new CommonDaoArc<String>();
		String sql2 = "select classNo from classinfo union select classNo from caf;";
		List<String> classNoList = tmpCDA.fetchList(connection, sql2, null);
		HashSet<String> hs = new HashSet<String>(classNoList);
		
		
		if (classInfo.getClassNo().equals("auto")) {
			Integer res =  (int) ((Math.random() * 9 + 1) * 1000000);
			while (hs.contains(res.toString())) {
				res =  (int) ((Math.random() * 9 + 1) * 1000000);				
			}
			params[0] = res.toString();
		}

		update(connection, sql, params);
	}

	@Override
	public List<ClassInfo> getClassInfoRecordsListFromApplication(Connection connection) throws SQLException {
		String sql = "select * from CAF;";
		return fetchList(connection, sql, null);
	}

	@Override
	public ClassInfo getClassInfoRecordFromApplication(Connection connection, String no) throws SQLException {
		String sql = "select * from caf where classNo = ?";
		Object[] params = {no};
		return fetch(connection, sql, params);
	}

	@Override
	public void deleteClassInfoRecordFromApplication(Connection connection, String no) throws SQLException {
		String sql = "delete from caf where classNo = ?";
		Object[] params = {no};
		update(connection, sql, params);
	}

	@Override
	public void approvedApplication(Connection connection, String no) throws SQLException {
		ClassInfo classInfo = getClassInfoRecordFromApplication(connection, no);
		add(connection, classInfo);
		deleteClassInfoRecordFromApplication(connection, no);
	}

	@Override
	public void refusedApplication(Connection connection, String no) throws SQLException {
		String emailAddr = getClassInfoRecordFromApplication(connection, no).getCreaterEmailAddr();
		deleteClassInfoRecordFromApplication(connection, no);
	}

	@Override
	public List<ClassInfo> getCreateClassRecordsList(Connection connection, String emailAddr) throws SQLException {
		String sql = "select * from classinfo where createrEmailAddr = ?;";
		Object[] params = {emailAddr};
		return fetchList(connection, sql, params);
	}

	@Override
	public Boolean isExist(Connection connection, String classNo) throws SQLException {
		String sql = "select count(*) from classinfo where classNo = ?;";
		Object[] params = {classNo};
		Long t = this.<Long>fetchScaler(connection, sql, params);
		return t == 1;
	}

	@Override
	public ClassInfo getCreateClassRecord(Connection connection, String classNo) throws SQLException {
		String sql = "select * from classinfo where classNo = ?;";
		Object[] params = {classNo};
		return fetch(connection, sql, params);
	}
	

}
