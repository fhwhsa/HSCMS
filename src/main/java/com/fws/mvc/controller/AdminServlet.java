package com.fws.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	// 跳转公告管理
	@SuppressWarnings("unused")
	private void turnToAnnoMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/announcement.jsp").forward(request, response);
	}
	
	// 跳转注册审核
	@SuppressWarnings("unused")
	private void turnToRegistMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<RegistrationRecord> records = adminService.getRecordsService();
		request.setAttribute("records", records);
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/registrationAssessment.jsp").forward(request, response);
	}  
    
	
	// 跳转班级管理
	@SuppressWarnings("unused")
	private void turnToClassMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/classAssessment.jsp").forward(request, response);
	}
	
	// 跳转用户管理
	@SuppressWarnings("unused")
	private void turnToUserMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/userManagement.jsp").forward(request, response);
	}
	
	// 更新系统公告
	@SuppressWarnings("unused")
	private void updateAnno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getParameter("context");
		context = new String(context.getBytes("iso-8859-1"), "utf-8");
		adminService.updateSysAnnoService(context);
		request.setAttribute("announcement", adminService.getSysAnnoService());
		request.getRequestDispatcher("WEB-INF/views/adminPage/announcement.jsp").forward(request, response);
	}
	
	@SuppressWarnings("unused")
	private void approved(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddr = request.getParameter("emailAddr");
		String userType = request.getParameter("userType");
		adminService.approvedService(emailAddr, userType);
		this.turnToRegistMJSP(request, response);
	}
	
	@SuppressWarnings("unused")
	private void refused(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailAddr = request.getParameter("emailAddr");
		String userType = request.getParameter("userType");
		adminService.refusedService(emailAddr, userType);
		this.turnToRegistMJSP(request, response);
	}
}

/*
insert into raf (name, passWord, emailAddr, userType, childList, classList) values 
("fws", "111", "aaa", "Teacher", "", ""),
("fws2", "111", "bbb", "Teacher", "", "class1,"),
("fws3", "111", "ccc", "Guardian", "", ""),
("fws4", "111", "ddd", "Guardian", "fws41,fws42", "class1,");

*/
