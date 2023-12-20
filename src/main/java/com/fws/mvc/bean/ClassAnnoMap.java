package com.fws.mvc.bean;

import java.util.Date;

// 班级通知映射
public class ClassAnnoMap {
	private String classNo;
	private String context;
	private Date createTimeStamp;
	public ClassAnnoMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ClassAnnoMap(String classNo, String context) {
		super();
		this.classNo = classNo;
		this.context = context;
		this.createTimeStamp = new Date();
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}
	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	
}
