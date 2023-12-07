package com.fws.mvc.service;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fws.mvc.bean.Guardian;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.bean.Teacher;
import com.fws.mvc.bean.User;
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.daoArc.RegisterDaoArc;
import com.fws.mvc.daoArc.UserDaoArc;
import com.fws.mvc.utils.JdbcTools;
import com.fws.mvc.utils.SendEmail;

public class IndexService {
	private static HashMap<String, String> verCode = null; // 存储每个邮箱对应的验证码
	private static RegisterDaoArc registerDaoArc = null;
    private static GlobalVarDaoArc globalVarDaoArc = null;
    private static UserDaoArc userDaoArc = null;

	public IndexService() {
		if (verCode == null)
			verCode = new HashMap<String, String>();
		if (registerDaoArc == null)
			registerDaoArc = new RegisterDaoArc();
		if (globalVarDaoArc == null)
			globalVarDaoArc = new GlobalVarDaoArc();
		if (userDaoArc == null)
			userDaoArc = new UserDaoArc();
	}

	
/* 登陆 *********************************************************************************************************************************/	
	
	// 登陆
	public Boolean loginService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String emailAddr = request.getParameter("emailAddr");
		String passWord = request.getParameter("password");
		String userType = request.getParameter("userType");
		Connection connection = null;
		Boolean res = false;
		try {
			connection = JdbcTools.getConnectionByPools();
			res = userDaoArc.checkPasswd(connection, emailAddr, passWord, userType);
			
			if (res) {
				// 处理显示账户信息
				HttpSession session = request.getSession();				
				session.setAttribute("currEmailAddr", emailAddr);
				session.setAttribute("currUserType", userType);
				session.setAttribute("currName", getUserNameService(emailAddr, userType));
				session.setAttribute("announcement", getSysAnnoService());
			}
			else 
				request.setAttribute("error", "邮箱或密码错误！！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return res;
	}
	
/*****************************************************************************************************************************************/
	
	
	
	
	
/* 找回密码 ********************************************************************************************************************************/
	
	// 找回密码发送验证码
	public void sendFindPasswdVerCodeService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String emailAddr = request.getParameter("emailAddr");
		String userType = request.getParameter("userType");
		System.out.println(userType);
		Connection connection = null;
		Boolean flag = false;
		String error = "";
		try {
			connection = JdbcTools.getConnectionByPools();
			flag = userDaoArc.isExist(connection, emailAddr, userType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		
		if (!flag) {
			request.setAttribute("flag", false); 			
			request.setAttribute("error", "不存在的邮箱！！");
		}
		else {
			String vcode = SendEmail.generateVerCode();
			verCode.put(emailAddr, vcode);
			SendEmail.sendMail(emailAddr, "找回密码验证码", vcode);
			request.setAttribute("flag", true);
		}
	}
	
	// 修改密码
	public Boolean changePasswdService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String vcode = request.getParameter("verCode");
		String emailAddr = request.getParameter("emailAddr");
		
		if (!verCode.containsKey(emailAddr) || !verCode.get(emailAddr).equals(vcode)) {
			request.setAttribute("error", "验证码错误");
			return false;
		}
		
		String passwd = request.getParameter("password");
		String userType = request.getParameter("userType");
		Connection connection = null;
		Boolean res = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			userDaoArc.updatePasswd(connection, emailAddr, userType, passwd);
			res = true;
		} catch (Exception e) {
			res = false;
			request.setAttribute("error", e.getMessage());
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return res;
	}
	
/*****************************************************************************************************************************************/
	
	
	
/* 注册 ***********************************************************************************************************************************/
	
	// 注册发送验证码
	public void sendRegisterVerCodeService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 检查是否为已经注册的邮箱
		String emailAddr = request.getParameter("emailAddr");
		String userType = request.getParameter("userType");
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
		SendEmail.sendMail(emailAddr, "注册验证码", vcode);
		request.setAttribute("flag", true); // 该servlet是点击获取验证码按钮启动的，改变flag值让其回到页面后按钮开始计时
	}
	
	// 家长注册（不经管理员审核）
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
			userDaoArc.add(connection, new Guardian(name, passwd, emailAddr, child), "Guardian");
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
			registerDaoArc.addApplication(connection, new RegistrationRecord(name, passwd, emailAddr));
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
	
/*****************************************************************************************************************************************/
	
	
	
	
	
/* 其它 ***********************************************************************************************************************************/
	
	// 获取系统公告
	public String getSysAnnoService() throws Exception {
		String anno = "";
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			anno = globalVarDaoArc.getSysAnnoContext(connection);
		} catch (Exception e) {
			anno = e.getMessage();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return anno;
	}
	
	// 获取用户对象
	public User getUserService(String emailAddr, String userType) throws Exception {
		User u = null;
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			u = userDaoArc.getUser(connection, emailAddr, userType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return u;
	}
	
	// 获取用户名称
	public String getUserNameService(String emailAddr, String userType) throws Exception {
		String res = "";
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			res = userDaoArc.getUserName(connection, emailAddr, userType);
		} catch (Exception e) {
			res = e.getMessage();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return res;
	}
	
/*****************************************************************************************************************************************/
}
