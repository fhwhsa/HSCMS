package com.fws.mvc.bean;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends User {	
	
	private String classList;

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
}
