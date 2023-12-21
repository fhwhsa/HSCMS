package com.fws.mvc.bean;

import java.util.Date;

public class ClassCommRecords {

	private String classNo;
	private String senderName;
	private String senderEmailAddr;
	private String context;
	private Date sendingDate;
	
	public ClassCommRecords() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassCommRecords(String classNo, String senderName, String senderEmailAddr, String context,
			Date sendingDate) {
		super();
		this.classNo = classNo;
		this.senderName = senderName;
		this.senderEmailAddr = senderEmailAddr;
		this.context = context;
		this.sendingDate = sendingDate;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmailAddr() {
		return senderEmailAddr;
	}

	public void setSenderEmailAddr(String senderEmailAddr) {
		this.senderEmailAddr = senderEmailAddr;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}


	
}
