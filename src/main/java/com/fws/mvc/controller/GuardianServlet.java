package com.fws.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.service.GuardianService;

/**
 * Servlet implementation class GuardianServlet
 */
@WebServlet("*.gdo")
public class GuardianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static GuardianService guardianService = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuardianServlet() {
        super();
        
        if (guardianService == null)
        	guardianService = new GuardianService();
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

	
	
	
	
/* 加入班级 ****************************************************************************************************************************************/
	
	// 转加入班级页
	@SuppressWarnings("unused")
	private void turnToJoinClassPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("WEB-INF/views/guardianPage/joinClass.jsp").forward(request, response);
	}
	
	// 提交加入班级申请
	@SuppressWarnings("unused")
	private void submitApplication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		guardianService.submitApplicationService(request, response);
		request.getRequestDispatcher("WEB-INF/views/guardianPage/joinClass.jsp").forward(request, response);
	}
	
/*************************************************************************************************************************************************/
	
	
	
	

/* 我的班级跳转处理 *********************************************************************************************************************************/

	// 跳转班级选择
	@SuppressWarnings("unused")
	private void turnToSelectClassJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		guardianService.initJoinedClassRecordList(request, response);
		request.getRequestDispatcher("WEB-INF/views/guardianPage/selectClass.jsp").forward(request, response);
	}
	
	// 转我的班级功能页
	@SuppressWarnings("unused")
	private void turnToMyClassJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		guardianService.initMyClassPage(request, response);
		request.getRequestDispatcher("WEB-INF/views/guardianPage/myClass.jsp").forward(request, response);
	}
	
	// 我的班级功能页功能选择
	@SuppressWarnings("unused")
	private void changeManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		guardianService.changeManagementPageService(request, response);
		request.getRequestDispatcher("WEB-INF/views/guardianPage/myClass.jsp").forward(request, response);
	}
	
/*************************************************************************************************************************************************/
	
}
