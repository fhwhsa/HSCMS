package com.fws.mvc.bean;

public class RegistrationRecord {
	private String name;
	private String passWord;
	private String emailAddr;
	
	public RegistrationRecord() {
		super();
	}
	
	public RegistrationRecord(String name, String passWord, String emailAddr) {
		super();
		this.name = name;
		this.passWord = passWord;
		this.emailAddr = emailAddr;
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

}
