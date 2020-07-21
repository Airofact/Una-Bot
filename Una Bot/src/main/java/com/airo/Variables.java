package com.airo;

import java.util.*;

/**
 * 此类用于储存Una Bot相关临时变量，退出Bot时即销毁
 */
public class Variables {
	//这条注释有什么意义吗
	static HashMap<String,Object> var=new HashMap<>();

	/**
	 * 检测是否运行
	 * @return 运行状态
	 */
	public static boolean isRunning(){
		return (boolean)var.get("running");
	}
}
