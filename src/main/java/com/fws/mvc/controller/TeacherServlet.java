package com.fws.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.service.TeacherService;

/**
 * Servlet implementation class TeacherServlet
 */
@WebServlet("*.tdo")
public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static TeacherService teacherService = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherServlet() {
        super();
        // TODO Auto-generated constructor stub
        if (teacherService == null)
        	teacherService = new TeacherService();
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
	
	
/* 创建班级 ***************************************************************************************************************************************/
	
	// 转创建班级页
	@SuppressWarnings("unused")
	private void turnToCreateClassJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.refreshSysAnnoService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/createClass.jsp").forward(request, response);
	}	
	
	// 创建
	@SuppressWarnings("unused")
	private void createClass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (teacherService.createClass(request, response)) 
			request.getRequestDispatcher("WEB-INF/views/teacherPage/success_t.jsp").forward(request, response);
		else 
			request.getRequestDispatcher("WEB-INF/views/teacherPage/createClass.jsp").forward(request, response);			
	}
	
/*************************************************************************************************************************************************/
	
	
	
	
	
/* 班级管理跳转处理 *********************************************************************************************************************/

	// 转班级选择（创建的）
	@SuppressWarnings("unused")
	private void turnToSelectCreateClassJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.initCreateClassData(request, response);
		teacherService.refreshSysAnnoService(request, response);
		request.setAttribute("jumpPath", new String("turnToClassManagementJSP")); // 用于表示在选择班级后是跳转班级管理还是我的班级
		request.getRequestDispatcher("WEB-INF/views/teacherPage/selectClass.jsp").forward(request, response);
	}
	
	// 转班级管理
	@SuppressWarnings("unused")
	private void turnToClassManagementJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.initClassManagementData(request, response);
		teacherService.refreshSysAnnoService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/classManagement.jsp").forward(request, response);
	}
	
	// 功能选择
	@SuppressWarnings("unused")
	private void changeManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.changeManagementPageService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/classManagement.jsp").forward(request, response);
	}

/*************************************************************************************************************************************/
	

	
	
	
/* 通知模块 ****************************************************************************************************************************/
	
	// 发布通知
	@SuppressWarnings("unused")
	private void postAnno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.postAnnoService(request, response);
//		request.setAttribute("page", "3");
		request.getRequestDispatcher("WEB-INF/views/teacherPage/classManagement.jsp").forward(request, response);
	}
	
	// 删除通知
	@SuppressWarnings("unused")
	private void deleteAnno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.deleteAnnoService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/classManagement.jsp").forward(request, response);
	}

/***************************************************************************************************************************************/
	
	
	
	
	
/* 审核模块 ******************************************************************************************************************************/
	
	// 通过申请
	@SuppressWarnings("unused")
	private void approvedApplication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.approvedApplicationService(request, response);
		request.setAttribute("page", "1");
		request.getRequestDispatcher("WEB-INF/views/teacherPage/classManagement.jsp").forward(request, response);
	}
	
	// 拒绝申请
	@SuppressWarnings("unused")
	private void refusedApplication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.refusedApplicationService(request, response);
		request.setAttribute("page", "1");
		request.getRequestDispatcher("WEB-INF/views/teacherPage/classManagement.jsp").forward(request, response);
	}
	
/****************************************************************************************************************************************/
	

	


/* 加入班级 ******************************************************************************************************************************/
	
	// 转加入班级页
	@SuppressWarnings("unused")
	private void turnToJoinClassPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.refreshSysAnnoService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/joinClass.jsp").forward(request, response);
	}
	
	// 提交加入班级申请
	@SuppressWarnings("unused")
	private void submitApplication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.submitApplicationService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/joinClass.jsp").forward(request, response);
	}
	
/***************************************************************************************************************************************/
	
	
	
	
	
/* 我的班级跳转处理 ********************************************************************************************************/
	
	// 转班级选择（加入的）
	@SuppressWarnings("unused")
	private void turnToSelectJoinedClassJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.refreshSysAnnoService(request, response);
		teacherService.initJoinedClassRecordList(request, response);
		request.setAttribute("jumpPath", new String("turnToMyClassJSP")); // 用于表示在选择班级后是跳转班级管理还是我的班级
		request.getRequestDispatcher("WEB-INF/views/teacherPage/selectClass.jsp").forward(request, response);
	}
	
	// 转我的班级功能页
	@SuppressWarnings("unused")
	private void turnToMyClassJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.initMyClassPage(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/myClass.jsp").forward(request, response);
	}
	
	// 我的班级功能页功能选择
	@SuppressWarnings("unused")
	private void changeClassPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("change");
		teacherService.changeMyClassPageService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/myClass.jsp").forward(request, response);
	}
	
/***********************************************************************************************************************/

	
	
	
	
/* 班级通知筛选 *************************************************************************************************************************************/
	
	// 筛选班级通知
	@SuppressWarnings("unused")
	private void filterClassAnno(HttpServletRequest request, HttpServletResponse response) throws Exception {
		teacherService.getClassAnnoListByFilter(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/myClass.jsp").forward(request, response);
	}

/**************************************************************************************************************************************************/
	
	
	
	
	
/* 班级交流 ******************************************************************************************************************************************/
	
	// 发布信息（班级交流）
	@SuppressWarnings("unused")
	private void postCommRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jumpPath = request.getParameter("jumpPath");
		teacherService.postCommRecordService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/" + jumpPath).forward(request, response);
	}
	
	// 筛选班级信息（班级交流）
	@SuppressWarnings("unused")
	private void filteCommRecords(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String jumpPath = request.getParameter("jumpPath");
		teacherService.filteCommRecordsService(request, response);
		request.getRequestDispatcher("WEB-INF/views/teacherPage/" + jumpPath).forward(request, response);		
	}
	
/****************************************************************************************************************************************************/
	
}
