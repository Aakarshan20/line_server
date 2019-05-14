package com.line.server.model;
import java.io.*;
import java.net.*;
import java.util.*;
import com.line.common.*;

public class LineServer {
	ServerSocket ss = null;
	Socket s = null;
	public LineServer() {
		try {
			System.out.println("server is listening........");
			ss = new ServerSocket(9999);
			while(true) {
				s = ss.accept();//等待連接
				System.out.println("socket got");
				
				//接收client發來的訊息
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				User u = (User)ois.readObject();
				
				System.out.println("id:" + u.getUserId());
				System.out.println("passwd:" + u.getUserPasswd());
				
				Message msg = new Message();
				//往回發
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				
				//密碼123即給登入(測試方便)
				if( u.getUserPasswd().equals("123")) {
					msg.setMesType("1");
					oos.writeObject(msg);
					
					//這裡就單開一個thread 讓該thread 與客戶端保持通訊
					ServerConClientThread scct = new ServerConClientThread(s);
					//將線程加入hashmap中
					ManageClientThread.addClientThread(u.getUserId(), scct);
					//啟動線程
					scct.start();
					
					//並通知其他在線用戶
					scct.notifyOther(u.getUserId());//傳入登入成功者的id 並通知其他在線的人
					
				}else {
					msg.setMesType("2");
					oos.writeObject(msg);
					
					//關閉連接
					s.close();
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
