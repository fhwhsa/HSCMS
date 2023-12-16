package com.fws.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.daoArc.UserDaoArc;
import com.fws.mvc.service.IndexService;
import com.fws.mvc.utils.SendEmail;

/**
 * Servlet implementation class indexServlet
 */
@WebServlet("*.do")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String TeacherType = "Teacher";
	private static final String GuardianType = "Guardian";
    private static IndexService indexService = null;  

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        if (indexService == null) 
        	indexService = new IndexService();  
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		String methodName = path.substring(1, path.length() - 3);
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}	
	
	
	
/* 登陆 *********************************************************************************************************************************/
	
	// 登陆
	@SuppressWarnings("unused")
	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userType = request.getParameter("userType");
		Boolean res = indexService.loginService(request, response);
		
		if (res) 		
			request.getRequestDispatcher("WEB-INF/views/" + userType + "Page/mainPage.jsp").forward(request, response);
		else 
			request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
/*****************************************************************************************************************************************/
	
	
	
	
	
/* 注册 ***********************************************************************************************************************************/
	
	// 转注册选择类型
	@SuppressWarnings("unused")
	private void turnToRegisterSelectTypeJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("/WEB-INF/views/registerPage/registerSelectType.jsp").forward(request, response);
	}
	
	// 转注册页面
	@SuppressWarnings("unused")
	private void turnToRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = "/WEB-INF/views/registerPage/register";
		if (request.getParameter("userType").equals(TeacherType))
			path += TeacherType;
		else 
			path += GuardianType;
		request.setAttribute("flag", false); // 通过点击注册进入, 改变flag值，即为不调用按钮计时函数
		request.getRequestDispatcher(path + ".jsp").forward(request, response);
	}

	// 发送验证码
	@SuppressWarnings("unused")
	private void sendRegisterVerCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = "/WEB-INF/views/registerPage/register" + request.getParameter("userType") + ".jsp";
		indexService.sendRegisterVerCodeService(request, response);
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	// 家长账号注册
	@SuppressWarnings("unused")
	private void registerGuardian(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (indexService.registerGuardianService(request, response)) {
			request.setAttribute("message", "注册成功");
			request.getRequestDispatcher("WEB-INF/views/registerPage/success_r.jsp").forward(request, response);
		}
		else {
			request.setAttribute("flag", false);
			request.getRequestDispatcher("WEB-INF/views/registerPage/registerGuardian.jsp").forward(request, response);
		}
	}
	
	// 老师账号注册
	@SuppressWarnings("unused")
	private void registerTeacher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (indexService.registerTeacherService(request, response)) {
			request.setAttribute("message", "注册申请已提交，清等待管理员审核，结果通过邮箱通知！");			
			request.getRequestDispatcher("WEB-INF/views/registerPage/success_r.jsp").forward(request, response);
		}
		else {
			request.setAttribute("flag", false);
			request.getRequestDispatcher("WEB-INF/views/registerPage/registerTeacher.jsp").forward(request, response);			
		}
	}
	
/*****************************************************************************************************************************************/
	
	
	
	
	
/* 找回密码 ********************************************************************************************************************************/
	
	// 转找回密码用户类型
	@SuppressWarnings("unused")
	private void turnToSelectUserTypeJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("WEB-INF/views/forgetPage/selectUserType.jsp").forward(request, response);
	}
	
	// 转找回密码
	@SuppressWarnings("unused")
	private void turnToForgetPasswdJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("WEB-INF/views/forgetPage/forgetPasswd.jsp?userType=" + request.getParameter("userType")).forward(request, response);
	}	
	
	// 发送验证码前检查邮箱是否存在
	@SuppressWarnings("unused")
	private void SendFindPasswdVerCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		indexService.sendFindPasswdVerCodeService(request, response);
		request.getRequestDispatcher("WEB-INF/views/forgetPage/forgetPasswd.jsp?userType=" + request.getParameter("userType")).forward(request, response);
	}
	
	// 修改密码
	@SuppressWarnings("unused")
	private void changePasswd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (indexService.changePasswdService(request, response)) 
			request.getRequestDispatcher("WEB-INF/views/forgetPage/success_f.jsp").forward(request, response);
		else
			request.getRequestDispatcher("WEB-INF/views/forgetPage/forgetPasswd.jsp?userType=" + request.getParameter("userType")).forward(request, response);
	}
	
/*****************************************************************************************************************************************/
}
