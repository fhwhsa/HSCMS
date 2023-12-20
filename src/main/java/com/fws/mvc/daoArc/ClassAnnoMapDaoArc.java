package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.fws.mvc.bean.ClassAnnoMap;
import com.fws.mvc.dao.ClassAnnoMapDao;

public class ClassAnnoMapDaoArc extends CommonDaoArc<ClassAnnoMap> implements ClassAnnoMapDao {

	@Override
	public void addAnno(Connection connection, String classNo, String context) throws SQLException {
		String sql = "insert into class_announcement_map (classNo, context, createTimeStamp) values (?, ?, ?);";
		Object[] params = {classNo, context, new Date()};
		update(connection, sql, params);
	}

	@Override
	public List<ClassAnnoMap> getAnnoList(Connection connection, String classNo) throws SQLException {
		String sql = "select * from class_announcement_map where classNo = ? order by createTimeStamp desc;";
		Object[] params = {classNo};
		return fetchList(connection, sql, params);
	}
	
	

}
