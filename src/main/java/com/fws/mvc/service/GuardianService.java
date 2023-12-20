package com.fws.mvc.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.bean.ClassApplicationRecord;
import com.fws.mvc.daoArc.ClassApplicationRecordDaoArc;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class GuardianService {

	private ClassInfoDaoArc classInfoDaoArc = null;
	private ClassApplicationRecordDaoArc classApplicationRecordDaoArc = null;
	
	public GuardianService() {
		if (classInfoDaoArc == null)
			classInfoDaoArc = new ClassInfoDaoArc();
		if (classApplicationRecordDaoArc == null)
			classApplicationRecordDaoArc = new ClassApplicationRecordDaoArc();
	}
	
/* 加入班级 ****************************************************************************************************************************************/

	public void submitApplicationService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classNo = request.getParameter("classNo");
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			if (!classInfoDaoArc.isExist(connection, classNo)) 
				request.setAttribute("mes", "不存在的班级编号");
			else {
				String emailAddr = (String) request.getSession().getAttribute("currEmailAddr");
				String name = (String) request.getSession().getAttribute("currName");
				ClassApplicationRecord record = new ClassApplicationRecord(emailAddr, name, classNo);
				if (classApplicationRecordDaoArc.hasSubmit(connection, record)) 
					request.setAttribute("mes",	"审核重复提交");
				else {
					classApplicationRecordDaoArc.addApplicationRecord(connection, record);
					request.setAttribute("mes", "申请已提交，请等待审核");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
/*************************************************************************************************************************************************/

}
