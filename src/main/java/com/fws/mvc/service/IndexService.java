package com.fws.mvc.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.utils.JdbcTools;
import com.fws.mvc.utils.SendEmail;

public class IndexService {
	private static HashMap<String, String> verCode = null;
	
	public IndexService() {
        if (verCode == null) {
        	verCode = new HashMap<String,String>();
        }
	}
	
	public void sendVerCodeService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String emailAddr = request.getParameter("emailAddr");
		String vcode = SendEmail.generateVerCode();
		verCode.put(emailAddr, vcode);
		SendEmail.sendMail(emailAddr, "验证码", vcode);
		request.setAttribute("flag", true); // 该servlet是点击获取验证码按钮启动的，改变flag值让其回到页面后按钮开始计时
	}
	
}
