package com.fws.mvc.bean;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {	
	
	private String emailAddr;
	private String classList;
	
	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Teacher(String name, String passWord, String emailAddr, String classList) {
		super(name, passWord);
		this.emailAddr = emailAddr;
		this.classList = classList;
		// TODO Auto-generated constructor stub
	}

	List<String> getClassList() {
		List<String> ans = new ArrayList<String>();
		for (int i = 0; i < classList.length();) {
			int j = i;
			while (j < classList.length() && classList.charAt(j) != ',') j++;
			ans.add(classList.substring(i, j - 1));
			i = j + 1;
		}
		return ans;
	}
	
	public String getClassListToString() {
		return this.classList;
	}

	public String getEmailAddr() {
		return emailAddr;
	}
	
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
}
