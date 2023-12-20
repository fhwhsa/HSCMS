package com.fws.mvc.bean;

// 加入班级申请
public class ClassApplicationRecord {

	private String emailAddr;
	private String name;
	private String classNo;
	public ClassApplicationRecord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClassApplicationRecord(String emailAddr, String name, String classNo) {
		super();
		this.emailAddr = emailAddr;
		this.name = name;
		this.classNo = classNo;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	
}
