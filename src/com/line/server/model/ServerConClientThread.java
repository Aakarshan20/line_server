/*
 * server 與某個client的通訊thread
 * */

package com.line.server.model;
import java.net.*;
import java.io.*;
import com.line.common.*;
import java.util.*;


public class ServerConClientThread extends Thread{
	Socket s;
	
	//把server和client的連接賦給 s
	public ServerConClientThread(Socket s) {
		this.s = s;
	}
	
	//讓該(用戶)線程去通知其他用戶
	public void notifyOther(String loggedUserId) {//上線的人去跟大家說我上線了
		Message m = new Message();
		m.setContent(loggedUserId);
		m.setMesType(MessageType.messageReturnOnlineFriend);
		//得到所有在線人的thread
		HashMap hm = ManageClientThread.hm;
		
		//創建迭代器
		Iterator it = hm.keySet().iterator();
		
		while(it.hasNext()) {
			//取出在線人的id
			String onlineUserId = it.next().toString();
			
			try {
				//給他更新上線列表
				ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThraed(onlineUserId).s.getOutputStream());
				m.setRecipient(onlineUserId);//發給在線上的人
				oos.writeObject(m);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	
	public void run() {
		while(true) {
			//這裡該thread可以接收client的訊息
			try{
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				Message msg = (Message)ois.readObject();
				
				//判斷client取得的消息
				switch(msg.getMesType()) {
					case MessageType.messageNormalMsg://如果是要轉發消息
						//取得接收方的通訊thread
						System.out.println("(1)client 收到:" + msg.getContent());
						ServerConClientThread scc = ManageClientThread.getClientThraed(msg.getRecipient());
						//轉發
						ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
						oos.writeObject(msg);
						break;
					case MessageType.messageGetOnlineFriend://如果是要取得上線中的好友列表
						//把在server的好友給client返回
						System.out.println("(2)client 收到:" + msg.getContent());
						String res = ManageClientThread.getAllOnlineUserId();
						Message m2 = new Message();
						
						
						m2.setMesType(MessageType.messageReturnOnlineFriend );
						m2.setContent(res);
						m2.setRecipient(msg.getSender());
						System.out.println("(3)client 收到:" + m2.getContent());
						
						ObjectOutputStream oos2 = new ObjectOutputStream(s.getOutputStream());
						oos2.writeObject(m2);
						break;
				}
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
