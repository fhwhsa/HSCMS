package com.fws.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.service.GlobalVarService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("*.ado")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static GlobalVarService globalVarService = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();

        if (globalVarService == null)
        	globalVarService = new GlobalVarService();
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

	@SuppressWarnings("unused")
	private void turnToAnnoMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", globalVarService.getSysAnno());
		request.getRequestDispatcher("WEB-INF/views/adminPage/announcement.jsp").forward(request, response);
	}
	
	@SuppressWarnings("unused")
	private void turnToRegistMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", globalVarService.getSysAnno());
		request.getRequestDispatcher("WEB-INF/views/adminPage/registrationAssessment.jsp").forward(request, response);
	}
	
	@SuppressWarnings("unused")
	private void turnToClassMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", globalVarService.getSysAnno());
		request.getRequestDispatcher("WEB-INF/views/adminPage/classAssessment.jsp").forward(request, response);
	}
	
	@SuppressWarnings("unused")
	private void turnToUserMJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("announcement", globalVarService.getSysAnno());
		request.getRequestDispatcher("WEB-INF/views/adminPage/userManagement.jsp").forward(request, response);
	}
}
