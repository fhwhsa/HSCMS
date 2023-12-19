package com.fws.mvc.bean;

public class UserClassMap {
	
	private String name;
	private String emailAddr;
	private String classNo;
	
	public UserClassMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserClassMap(String name, String emailAddr, String classNo) {
		super();
		this.name = name;
		this.emailAddr = emailAddr;
		this.classNo = classNo;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	@Override
	public String toString() {
		return "UserClassMap [name=" + name + ", emailAddr=" + emailAddr + ", classNo=" + classNo + "]";
	}

	
}
