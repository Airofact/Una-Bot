package com.airo.message;

public class GroupMessage extends Message{
	long group=0L;
	public GroupMessage(long group,long id,String msg){
		this.group=group;
		this.id=id;
		this.msg=msg;
	}

	public long getGroup() {
		return group;
	}
}
