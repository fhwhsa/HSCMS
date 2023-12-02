package com.fws.mvc.bean;

public class User {
	private String name;
	private String passWord;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String name, String passWord) {
		super();
		this.name = name;
		this.passWord = passWord;
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
	
}