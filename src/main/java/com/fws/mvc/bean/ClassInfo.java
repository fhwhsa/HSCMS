	package com.fws.mvc.bean;

import java.util.Date;

public class ClassInfo {
	private String classNo;
	private String className;
	private String creater;
	private Date createTimeStamp;
	private String createrEmailAddr;
	
	public ClassInfo() {
		super();
	}
	
	public ClassInfo(String classNo, String className, String creater, String createrEmailAddr) {
		super();
		this.classNo = classNo;
		this.className = className;
		this.creater = creater;
		this.createTimeStamp = new Date();
		this.createrEmailAddr = createrEmailAddr;
	}
	
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}
	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	public String getCreaterEmailAddr() {
		return createrEmailAddr;
	}
	public void setCreaterEmailAddr(String createrEmailAddr) {
		this.createrEmailAddr = createrEmailAddr;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	
}
