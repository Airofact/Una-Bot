package com.airo.message;

public abstract class Message {
	String msg="";
	long id=0L;
	public String getMsg() {
		return msg;
	}
	public long getId(){
		return id;
	}
}
