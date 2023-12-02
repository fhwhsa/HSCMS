package com.fws.mvc.bean;

import java.util.ArrayList;
import java.util.List;

public class Guardian extends User {
	
	private String emailAddr;
	private String childList;
	private String classList;

	

	public Guardian() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Guardian(String name, String passWord, String emailAddr, String childList, String classList) {
		super(name, passWord);
		this.emailAddr = emailAddr;
		this.childList = childList;
		this.classList = classList;
		// TODO Auto-generated constructor stub
	}
	

	public void setChildList(String childList) {
		this.childList = childList;
	}

	List<String> getChildList() {
		List<String> ans = new ArrayList<String>();
		for (int i = 0; i < childList.length();) {
			int j = i;
			while (j < childList.length() && childList.charAt(j) != ',') j++;
			ans.add(childList.substring(i, j - 1));
			i = j + 1;
		}
		return ans;
	}
	
	public String getChildListToString() {
		return this.childList;
	}
	
	public void setClassList(String classList) {
		this.classList = classList;
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
