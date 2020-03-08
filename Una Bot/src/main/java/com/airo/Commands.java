package com.airo;

import static java.lang.Math.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;

import static com.sobte.cqp.jcq.event.JcqApp.CC;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

import static com.airo.Variables.*;

public class Commands {

	static {
		var.put("cao",false);
		var.put("running",true);
	}

	public static void CheckPvtMsgs(long qqId,String msg){
		if(qqId==1789706995) {
			String[] msgs={
					"有什么事在群里说就行嘛",
					"什么事要悄悄的找我啊",
					"I_I?"
			};
			sendPrivateFromSet(qqId,msgs);
		}else {
			sendPrivate(qqId, "有事请加Q群813390057");
			sendPrivate(qqId, "没事请gun");
		}
	}

	public static void CheckGrpMsgs(long group, long qqId, String msg) throws IOException {
		//Create by Airo
        /*
            Airo的奇妙bot，于2020-2-28日创建
         */
		//Start Action Define

		String[] args = msg.split(" ");
		String cmd = args[0];

		//特殊指令
		switch (cmd){
			case "/una":{
				if(CQ.getGroupMemberInfo(group,qqId).getAuthority()==2 || qqId==1789706995) {
					if (args.length > 1) {
						if (args[1].equals("on")) {
							var.replace("running", true);
						} else if (args[1].equals("off")) {
							var.replace("running", false);
							sendGroup(group, "那我就先去歇会啦");
						}
					} else {
						sendGroup(group, "请输入一个参数");
					}
				}else {
					sendGroup(group,"你没有权限哦");
				}
				break;
			}
			case "/state":{
				if (qqId == 1789706995) {
					if (args.length > 1) {
						sendGroup(group, var.get(args[1]).toString());
					} else {
						sendGroup(group, "请输入一个参数");
					}
				}
				break;
			}
			case "/help":{
				String[] basecmds = {
						"/una on\\off",			" 开启或关闭bot",				"\n",
						"/send group message",	" 通过bot往另一个群里发送消息",	"\n",
						"/luck",				" 查看今日运势",					"\n",
						"/cp",					" 查看今日cp",					"\n",
						"/lstg api\\教程",		" 查看LSTG相关参考",				"\n",
				};
				String sendcmd = "";
				for (String str : basecmds) {
					sendcmd += str;
				}
				sendGroup(group, sendcmd);
			}
		}

		//检测是否开启
		if(!(Boolean) var.get("running")) return;

		//日常指令
		switch (cmd) {
			case "/send": {
				if (qqId == 1789706995) {
					if (args.length > 2) {
						if(args[1].matches("[0-9]")){
							sendGroup(Long.valueOf(args[1]), args[2]);
						}
					} else {
						sendGroup(group, "请输入两个参数");
					}
				}
				break;
			}
			case "/luck":{
				LinkedList<long[]> data= Una.loadData();
				String resultMsg="failed";
				for(long[] user:data){
					if((group==user[0])&&(qqId==user[1])){
						resultMsg=CC.at(qqId)+" 你今天的运势有:\n";
						for (int i = 0; i < user[3]; i++) {
							resultMsg+="★";
						}
						for (int i = 0; i < 10-user[3]; i++) {
							resultMsg+="☆";
						}
						resultMsg+="颗星哦";
					}
				}
				sendGroup(group, resultMsg);
				String[] msgs={
						"今天也是好运气呢",
						"运气什么的都无所谓啦",
						"运势什么的都是随机啦，不要太在意哦"
				};
				sendGroupFromSet(group,msgs);
				break;
			}
			case "/cp": {
				LinkedList<long[]> data= Una.loadData();
				String resultMsg="";
				for(long[] user:data){
					if((group==user[0])&&(qqId==user[1])){
						resultMsg=CC.at(qqId)+" 你今天适合跟 "+CQ.getGroupMemberInfo(group,user[2]).getCard()+" 在一起哦";
					}
				}
				sendGroup(group,resultMsg);
				break;
			}
			case "/data":{
				if (qqId == 1789706995) {
					if (args.length > 1) {
						if (args[1].equals("get")) {
							LinkedList<long[]> data = Una.loadData();
							String resultMsg = "";
							for (long[] user : data) {
								for (int i = 0; i < user.length; i++) {
									resultMsg += user[i];
									resultMsg += " ";
								}
								resultMsg += "\n";
							}
							sendGroup(group, resultMsg);
						} else if (args[1] .equals("reset")) {
							Una.resetData();
							sendGroup(group, "用户数据已重置");
						}
					}else {
						sendGroup(group, "请输入一个参数");
					}
				}
				break;
			}
			case "/lstg": {
				if (args.length > 1) {
					if (args[1].equals("api")) {
						sendGroup(group, "https://github.com/Xiliusha/LuaSTG-EX-Plus/wiki/LuaSTG-Plus-LuaAPI");
						sendGroup(group, "https://github.com/Xiliusha/LuaSTG-EX-Plus/wiki/LuaSTG-EX-Plus-LuaAPI");
						sendGroup(group, "不要总是这么懒，自己去翻收藏很麻烦吗？");
					} else if (args[1].equals("教程")) {
						sendGroup(group, "https://www.bilibili.com/video/av62731749");
						sendGroup(group, "图书教程，请");
					}else {
						if(msg.matches(".*\\?.*")){
							sendGroup(group,CC.at(1789706995)+" 出来看看怎么回事");
						}
					}
				}else {
					sendGroup(group, "请输入一个参数");
				}
				break;
			}
			case "作业写完啦": {
				if (qqId == 1789706995) {
					Calendar cal = Calendar.getInstance();
					int hour = cal.get(Calendar.HOUR_OF_DAY);
					if (hour > 22 && hour < 4) {
						sendGroup(group, "嗯嗯，快去睡觉哦");
					} else if (hour > 4 && hour > 14) {
						sendGroup(group, "鬼信你啦，哪有这么早做完作业的");
					} else {
						sendGroup(group, "嗯嗯，好，去玩吧~");
					}
				}
				break;
			}
			case "Una": {
				if (qqId == 1789706995) {
					String[] msgs = {
							"Airo？",
							"嗯，怎么了",
							"?"
					};
					sendGroupFromSet(group, msgs);
				} else {
					String[] msgs = {
							"是" + CQ.getGroupMemberInfo(group, qqId).getCard() + "啊，有什么事吗",
							"啊？",
							"……？"
					};
					sendGroupFromSet(group, msgs);
				}
				break;
			}
			case "[CQ:at,qq=757212167] 老婆": {
				if (qqId == 1789706995) {
					sendGroup(group, "贴贴♥");
				}
				break;
			}
			case "老婆": {
				if (qqId == 1789706995) {
					String[] cmds = {
							"别这样啦，乐乐才是你老婆",
							"我才不是你老婆！",
							"呐……怎么了",
							"嗯呐……",
							"T_T"
					};
					sendGroupFromSet(group, cmds);
				} else {
					String[] cmds = {
							"群友们还不够你搞吗（",
							"老婆怪gunna",
							"？自己找一个不好吗",
							"额，你谁啊"
					};
					sendGroupFromSet(group, cmds);
				}
				break;
			}
			case "sjf": {
				if (qqId == 1789706995) {
					sendGroup(group, "终于想起来要做四季赋啦");
					sendGroup(group, "要好好做哦");
					sendGroup(group, "不然ogg会伤心的~");
					String[] basecmds = {
							"SJF制作文档：", "\n",
							"https://docs.qq.com/doc/DVk9OdWpkWU5sQXhy", "\n",
							"制作进度：", "\n",
							"https://docs.qq.com/sheet/DWUNYZ3hKbXlPS2h3", "\n",
							"角色定义文稿：", "\n",
							"https://docs.qq.com/doc/DVlpWWHhPTWJZRW1s", "\n",
							"制作角色列表：", "\n",
							"https://docs.qq.com/doc/DVm1GVEhsRGtWZ3lE", "\n",
							"符卡补足与吐槽语：", "\n",
							"https://docs.qq.com/sheet/DVkZzcXpnV2Zpb3Fw", "\n",
							"判定记录列表：", "\n",
							"https://docs.qq.com/doc/DVndRQlRZdmxpVFBF", "\n",
							"预期的分成相关：", "\n",
							"https://docs.qq.com/sheet/DVnJuSHdvZHJZcURD", "\n",
							"制作任务：", "\n",
							"https://docs.qq.com/sheet/DVkhBTXJ1WXVYRmpr", "\n"
					};
					String sendcmd = "";
					for (String str : basecmds) {
						sendcmd += str;
					}
					sendGroup(group, sendcmd);
				}
				break;
			}
			case "乐乐今天更了吗": {
				if (CQ.getGroupMemberInfo(group, 757212167L) == null) {
					sendGroup(group, "乐乐不在这个群里啊");
				} else {
					sendGroup(group, CC.at(757212167L) + " 乐乐你的阿空mod做完了吗");
				}
				break;
			}
			case "觉哥今天更了吗": {
				if (CQ.getGroupMemberInfo(group, 1324851344L) == null) {
					sendGroup(group, "鬼知道哦，他又不在群里");
				} else {
					sendGroup(group, "觉哥快出来复刻");
				}
				break;
			}
			case "图书今天更了吗": {
				sendGroup(group, (CC.at(3082857745L)) + " " + "快出来更新啦!");
				break;
			}
			case "Airo今天更了吗": {
				if (qqId == 1789706995) {
					sendGroup(group, "?更nm快去学习—_—");
				} else {
					sendGroup(group, "Airo要中考了哦，最近都不会做新东西了");
				}
				break;
			}
			case "猫猫是世界的珍宝☆":{
				sendGroup(group,"隔壁家的傻猫…？");
				break;
			}
			default: {
				if(msg.matches(".*"+CQ.getGroupMemberInfo(group,3033365817L).getCard()+".*射爆.*")){
					sendGroup(group,"呐…………（羞");
				}
				else if(msg.matches(".*"+CQ.getGroupMemberInfo(group,3033365817L).getCard()+".*抱.*")){
					sendGroup(group,"嗯呐…（抱紧");
				}
				else if(msg.matches(".*nmsl.*")){
					if(msg.matches(".*"+CQ.getGroupMemberInfo(group,3033365817L).getCard()+".*")) {
						sendGroup(group, "你骂谁呢你");
					}else{
						sendGroup(group, "别骂人啊好好说话");
					}
				}
				else if((msg.matches("草"))&&(!(Boolean) var.get("cao"))){
					sendGroup(group,"草");
					var.replace("cao",true);
					new Task(()->{
						try {
							Thread.sleep(10000);
							var.replace("cao",false);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				}
				else if(msg.matches(".*ghs.*")){
					sendGroup(group,"哪里ghs，我也要看！");
				}
				else if (qqId == 1789706995) {
					if (group == 813390057L) {
						String[] cmds = {
								"你作业做完了吗",
								"别水群了—_—",
								"有那么多时间怎么不去写作业",
								"Airo快去写作业！",
								"本周份的SJF还没做呐！"
						};
						sendGroupFromSet(group, cmds);
					}
				}
				break;
			}
		}

		//End
	}

	/**
	 * @param type	发送类型
	 * @param id	目标ID
	 * @param msg	内容
	 */
	public static void send(String type,long id,String msg){
		if(type.equals("group")){
			CQ.sendGroupMsg(id,msg);
		}else {
			CQ.sendPrivateMsg(id, msg);
		}
	}

	public static void sendGroupFromSet(long group,String[] msgs){
		sendFromSet("group",group,msgs);
	}

	public static void sendPrivateFromSet(long id,String[] msgs){
		sendFromSet("private",id,msgs);
	}

	public static void sendFromSet(String type,long id,String[] msgs){
		send(type,id,msgs[(new Random().nextInt(msgs.length))]);
	}

	public static void sendGroup(long group,String msg){
		send("group",group,msg);
	}

	public static void sendPrivate(long id,String msg){
		send("private",id,msg);
	}
}

class Task extends Thread{
	Task(Runnable func){
		super(func);
		start();
	}
}