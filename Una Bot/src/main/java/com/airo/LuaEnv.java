package com.airo;

import static com.airo.Una.io;

import static com.sobte.cqp.jcq.event.JcqApp.CQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Set;

public class LuaEnv{
	Globals GLOBAL;
	String path;
	LuaEnv(){
		GLOBAL = JsePlatform.standardGlobals();
		path = Una.appDirectory+"src\\";
		CQ.logInfo("LuaEnv",path);
		//解析json
		JSONObject jsonObject = LoadJSON("lib.json");
		CQ.logInfo("LuaEnv",Boolean.toString(jsonObject!=null));
		Set<String> keySet=jsonObject.keySet();
		//加载依赖库
		for(String key: keySet){
			LoadLib(key,jsonObject.getString(key));
		}
		//正式加载lua主逻辑
		load("main.lua");
		load("define.lua");
	}

	/**
	 * 加载依赖库到lua环境中
	 * @param name lua中的类名
	 * @param path 类路径
	 */
	public void LoadLib(String name,String path){
		try {
			set(name, new LuaUserdata(Class.forName(path)));
		}catch (ClassNotFoundException e){
			CQ.logError("LuaEvent",e.getMessage());
		}
	}

	/**
	 * 解析json
	 * @param name 文件名
	 * @return JSONObject
	 */
	public JSONObject LoadJSON(String name){
		io.Open(path+name);
		if(!io.CheckFile()){
			CQ.logWarning("LuaEnv","文件不存在，请检查");
		}
		String jsonStr=io.ReadAll();
		io.Dispose();
		return JSON.parseObject(jsonStr);
	}

	/**
	 * 加载lua文件
	 * @param file 文件的相对路径
	 */
	public void load(String file){
		try {
			GLOBAL.loadfile(path + file).call();
		}catch (Exception e){
			CQ.logError("LuaEnv","[Init Errot]"+e.getMessage());
		}
	}

	/**
	 * 获取lua环境中的一个变量
	 * @param name 变量名
	 * @return
	 */
	public LuaValue get(String name){
		return GLOBAL.get(name);
	}

	/**
	 * 设置lua环境中的一个变量
	 * @param name 变量名
	 * @param value 值
	 */
	public void set(String name,LuaValue value){
		GLOBAL.set(name,value);
	}


	public LuaString string(String s){return LuaString.valueOf(s);}
	public LuaNumber number(int i){
		return LuaNumber.valueOf(i);
	}
	public LuaNumber number(float i){
		return LuaNumber.valueOf(i);
	}
	public LuaNumber number(double i){
		return LuaNumber.valueOf(i);
	}
	public LuaNumber number(long i){
		return LuaNumber.valueOf(i);
	}
}
