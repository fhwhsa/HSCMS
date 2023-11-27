package com.fws.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private static IndexService service = null;  
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        if (service == null) {
        	service = new IndexService();  
        }
        // TODO Auto-generated constructor stub
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
	
	
	
	// 登陆
	@SuppressWarnings("unused")
	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().print("hello");
	}

	
	// 登陆转注册选择类型
	@SuppressWarnings("unused")
	private void turnToRegisterSelectTypeJSP(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getRequestDispatcher("/WEB-INF/views/registerSelectType.jsp").forward(request, response);
	}
	
	// 选择类型后转注册页面
	@SuppressWarnings("unused")
	private void turnToRegister(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = "/WEB-INF/views/register";
		if (request.getParameter("usertype").equals(TeacherType))
			path += TeacherType;
		else 
			path += GuardianType;
		request.setAttribute("flag", false); // 通过点击注册进入, 改变flag值，即为不调用按钮计时函数
		request.getRequestDispatcher(path + ".jsp").forward(request, response);
	}
	
	@SuppressWarnings("unused")
	private void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	
	@SuppressWarnings("unused")
	private void sendVerCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = "/WEB-INF/views/register" + request.getParameter("role") + ".jsp";
		service.sendVerCodeService(request, response);
		request.getRequestDispatcher(path).forward(request, response);
	}
}
