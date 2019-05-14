package com.line.server.model;
/*
 * 使用hashmap 實現對client thread的管理
 * */
import java.util.*;
public class ManageClientThread {
	//使用泛型 製作hashmap
	public static HashMap hm = new HashMap<String , ServerConClientThread>();
	
	//向hashmap中加入一個客戶端通訊thread
	public static void addClientThread(String uid, ServerConClientThread clientThread) {
		hm.put(uid, clientThread);
	}
	
	//根據uid 返回該用戶的線程
	public static ServerConClientThread getClientThraed(String uid) {
		return (ServerConClientThread)hm.get(uid);
	}
	
	//返回當前在線用戶
	public static String getAllOnlineUserId() {
		//使用迭代器遍歷HashMap
		Iterator it = hm.keySet().iterator();
		String res = "";
		while(it.hasNext()) {
			res += it.next().toString() + " ";
		}
		return res;
	}
}
