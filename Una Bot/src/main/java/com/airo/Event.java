package com.airo;

import com.airo.message.Message;
import com.alibaba.fastjson.JSONObject;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import static com.airo.Una.*;

import static com.sobte.cqp.jcq.event.JcqApp.CQ;

public class Event {
	public static final int EVENT_INIT = 1;
	public static final int EVENT_GROUP_CHECK = 2;
	public static final int EVENT_PRIVATE_CHECK = 3;
	public static boolean EXCEPTION_REPORT = true;
	public static void DoEvent(int id, LuaValue... args){
		CQ.logInfo("LuaEvent","Event ID:"+id);
		try {
			lua.load("define.lua");
			LuaTable events = (LuaTable) lua.get("events");
			events.get(id).invoke(LuaTable.varargsOf(args));
		}catch (Exception e){
			CQ.logWarning("LuaEvent",e.getMessage());
		}
	}
}
