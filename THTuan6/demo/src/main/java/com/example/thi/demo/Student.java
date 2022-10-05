package com.example.thi.demo;

import java.io.Serializable;

public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	private String ten;
	private String mess;
	
	public Student() {
		super();
	}
	public Student(String ten, String mess) {
		super();
		this.ten = ten;
		this.mess = mess;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getMess() {
		return mess;
	}
	public void setMess(String mess) {
		this.mess = mess;
	}
	
	@Override
	public String toString() {
		return "Student [ten=" + ten + ", mess=" + mess + "]";
	}
	

}
