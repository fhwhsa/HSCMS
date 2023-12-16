package com.fws.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("*.ado")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static AdminService adminService = null;  
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        
        if (adminService == null)
        	adminService = new AdminService();
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	doGet(request, response);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		String methodName = path.substring(1, path.length() - 4);
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
/* 公告 *****************************************************************************************************************************/
	
	// 跳转公告管理
	@SuppressWarnings("unused")
	private void turnToAnnoMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/announcement.jsp").forward(request, response);
	}
	
	// 更新系统公告
	@SuppressWarnings("unused")
	private void updateAnno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getParameter("context");
		context = new String(context.getBytes("iso-8859-1"), "utf-8");
		adminService.updateSysAnnoService(context);
		request.getSession().setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/announcement.jsp").forward(request, response);
	}

/************************************************************************************************************************************/	
	
	
	

	
/* 班级管理 **********************************************************************************************************************************/
	
	// 跳转班级管理
	@SuppressWarnings("unused")
	private void turnToClassMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ClassInfo> records = adminService.getClassInfoRecordsService();
		request.setAttribute("records", records);
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/classAssessment.jsp").forward(request, response);
	}
	
	// 同意申请
	@SuppressWarnings("unused")
	private void approvedClassApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classNo = request.getParameter("classNo");
		String emailAddr = request.getParameter("emailAddr");
		adminService.approvedClassApplicationService(classNo, emailAddr);
		this.turnToClassMJSP(request, response);
	}
	
	// 拒绝
	@SuppressWarnings("unused")
	private void refusedClassApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classNo = request.getParameter("classNo");
		String emailAddr = request.getParameter("emailAddr");
		adminService.refusedClassApplication(classNo, emailAddr);
		this.turnToClassMJSP(request, response);
	}

/********************************************************************************************************************************************/
	
	

	
/* 老师用户信息审核 *************************************************************************************************************************************/
	
	// 跳转注册审核
	private void turnToRegistMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<RegistrationRecord> records = adminService.getRegisterRecordsService();
		request.setAttribute("records", records);
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/registrationAssessment.jsp").forward(request, response);
	}

	// 同意注册
	@SuppressWarnings("unused")
	private void approvedRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddr = request.getParameter("emailAddr");
		adminService.approvedRegistrationService(emailAddr);
		this.turnToRegistMJSP(request, response);
	}	
	
	// 拒绝
	@SuppressWarnings("unused")
	private void refusedRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddr = request.getParameter("emailAddr");
		adminService.refusedRegistrationService(emailAddr);
		this.turnToRegistMJSP(request, response);
	}
	
/******************************************************************************************************************************************************/
}

