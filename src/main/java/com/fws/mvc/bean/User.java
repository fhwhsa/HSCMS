package com.fws.mvc.bean;

public class User {
	private String name;
	private String passWord;
	private String emailAddr;
	private String userType;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String name, String passWord, String emailAddr, String userType) {
		super();
		this.name = name;
		this.passWord = passWord;
		this.emailAddr = emailAddr;
		this.userType = userType;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", emailAddr=" + emailAddr + "]";
	}
	
}