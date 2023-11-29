package com.fws.mvc.service;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.daoArc.AdministratorDaoArc;
import com.fws.mvc.utils.JdbcTools;
import com.fws.mvc.utils.SendEmail;

public class IndexService {
	private static HashMap<String, String> verCode = null; // 存储每个邮箱对应的验证码
	private static AdministratorDaoArc administratorDaoArc = null;
	
	public IndexService() {
        if (verCode == null) 
        	verCode = new HashMap<String,String>();
        if (administratorDaoArc == null)
        	administratorDaoArc = new AdministratorDaoArc();
	}
	
	// 发送验证码
	public void sendVerCodeService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String emailAddr = request.getParameter("emailAddr");
		String vcode = SendEmail.generateVerCode();
		verCode.put(emailAddr, vcode);
		SendEmail.sendMail(emailAddr, "验证码", vcode);
		request.setAttribute("flag", true); // 该servlet是点击获取验证码按钮启动的，改变flag值让其回到页面后按钮开始计时
	}
	
	
	// 登陆
	public Boolean loginService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userType = request.getParameter("usertype");
		String methodName = userType + "Login";
		Boolean res = false;
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			res = (Boolean) method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	// 管理员登陆
	public Boolean adminLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("username");
		String passwd = request.getParameter("password");
		Boolean res = false;
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			res = administratorDaoArc.check(connection, name, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return res;
	}
	
	// 家长登陆
	public void teacherLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	
	// 老师登陆
	public void guardianLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
}
