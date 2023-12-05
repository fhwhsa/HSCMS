package com.fws.mvc.service;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.daoArc.AdministratorDaoArc;
import com.fws.mvc.daoArc.CommonDaoArc;
import com.fws.mvc.daoArc.RegisterDaoArc;
import com.fws.mvc.utils.JdbcTools;
import com.fws.mvc.utils.SendEmail;

public class IndexService {
	private static HashMap<String, String> verCode = null; // 存储每个邮箱对应的验证码
	private static AdministratorDaoArc administratorDaoArc = null;
	private static RegisterDaoArc registerDaoArc = null;
    private static GlobalVarService globalVarService = null;

	public IndexService() {
		if (verCode == null)
			verCode = new HashMap<String, String>();
		if (administratorDaoArc == null)
			administratorDaoArc = new AdministratorDaoArc();
		if (registerDaoArc == null)
			registerDaoArc = new RegisterDaoArc();
        if (globalVarService == null)
        	globalVarService = new GlobalVarService();
	}

	// 发送验证码
	public void sendVerCodeService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 检查是否为已经注册的邮箱
		String emailAddr = request.getParameter("emailAddr");
		String userType = request.getParameter("role");
		Connection connection = null;
		Boolean flag = false;
		try {
			connection = JdbcTools.getConnectionByPools();
			flag = registerDaoArc.isEAddrExist(connection, userType, emailAddr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}

		if (flag) {
			request.setAttribute("flag", false); 
			request.setAttribute("error", "邮箱已被使用或审核还未通过");
			return;
		}

		String vcode = SendEmail.generateVerCode();
		verCode.put(emailAddr, vcode);
		SendEmail.sendMail(emailAddr, "验证码", vcode);
		request.setAttribute("flag", true); // 该servlet是点击获取验证码按钮启动的，改变flag值让其回到页面后按钮开始计时
	}

	// 登陆
	public Boolean loginService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userType = request.getParameter("usertype");
		String methodName = userType + "LoginService";
		Boolean res = false;
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			res = (Boolean) method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 管理员登陆
	public Boolean adminLoginService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("username");
		String passwd = request.getParameter("password");
		Boolean res = false;
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			res = administratorDaoArc.isExist(connection, name, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return res;
	}

	// 家长登陆
	public void teacherLoginService(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	// 老师登陆
	public void guardianLoginService(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}
	
	// 家长注册
	public Boolean registerGuardianService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String emailAddr = request.getParameter("emailAddr");
		String vcode = request.getParameter("verCode");
		
		if (!verCode.containsKey(emailAddr) || !verCode.get(emailAddr).equals(vcode)) {
			request.setAttribute("error", "验证码错误");
			return false;
		}
		
		String passwd = request.getParameter("password");
		String name = request.getParameter("userName");
		String child = request.getParameter("stuName");
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			registerDaoArc.addApplication(connection, new RegistrationRecord(name, passwd, emailAddr, "Guardian", child));
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			connection.setAutoCommit(true);
			request.setAttribute("error", e.getMessage());
			return false;
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
		}
		
		return true;
	}
	
	// 老师注册
	public Boolean registerTeacherService(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String emailAddr = request.getParameter("emailAddr");
		String vcode = request.getParameter("verCode");
		
		if (!verCode.containsKey(emailAddr) || !verCode.get(emailAddr).equals(vcode)) {
			request.setAttribute("error", "验证码错误");
			return false;
		}
		
		String passwd = request.getParameter("password");
		String name = request.getParameter("userName");
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			registerDaoArc.addApplication(connection, new RegistrationRecord(name, passwd, emailAddr, "Teacher"));
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			connection.setAutoCommit(true);
			request.setAttribute("error", e.getMessage());
			return false;
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
		}
		
		return true;
	}
	
	// 获取系统公告
	public String getSysAnno() {
		return globalVarService.getSysAnno();
	}
}
