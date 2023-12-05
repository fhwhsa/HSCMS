package com.fws.mvc.bean;

public class RegistrationRecord {
	private String name;
	private String passWord;
	private String emailAddr;
	private String userType;
	private String childList;
	private String classList;
	
	public RegistrationRecord() {
		super();
	}
	
	public RegistrationRecord(String name, String passWord, String emailAddr, String userType) {
		super();
		this.name = name;
		this.passWord = passWord;
		this.emailAddr = emailAddr;
		this.userType = userType;
		this.childList = this.classList = "";
	}
	
	public RegistrationRecord(String name, String passWord, String emailAddr, String userType, String childList) {
		super();
		this.name = name;
		this.passWord = passWord;
		this.emailAddr = emailAddr;
		this.userType = userType;
		this.childList = childList + ",";
		this.classList = "";
	}
	
	public RegistrationRecord(String name, String passWord, String emailAddr, String userType, String childList, String classList) {
		super();
		this.name = name;
		this.passWord = passWord;
		this.emailAddr = emailAddr;
		this.userType = userType;
		this.childList = childList + ",";
		this.classList = classList + ",";
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
	public String getChildListToString() {
		return childList;
	}
	public void setChildList(String childList) {
		this.childList = childList;
	}
	public String getClassListToString() {
		return classList;
	}
	public void setClassList(String classList) {
		this.classList = classList;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "RegistrationRecord [name=" + name + ", passWord=" + passWord + ", emailAddr=" + emailAddr
				+ ", childList=" + childList + ", classList=" + classList + ", userType=" + userType + "]";
	}

}
