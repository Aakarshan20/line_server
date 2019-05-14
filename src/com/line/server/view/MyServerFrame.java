package com.line.server.view;
/*
 * 
 * 可以開啟/關閉server 與看到在線用戶，並管理與監控且強制某用戶下線
 * */
import org.json.*;

import javax.swing.*;

import com.line.server.model.LineServer;

import java.awt.*;
import java.awt.event.*;
public class MyServerFrame extends JFrame implements ActionListener{

	JButton startServerBtn, shutDownServerBtn;//服務器開關
	JPanel jp1;
	
	LineServer ls = null;
	
	public MyServerFrame() {
		initLayout();
	}
	
	private void initLayout() {
		
		startServerBtn = new JButton("啟動服務器");
		startServerBtn.addActionListener(this);
		startServerBtn.setActionCommand("start");
		
		shutDownServerBtn = new JButton("關閉服務器");
		shutDownServerBtn.addActionListener(this);
		shutDownServerBtn.setActionCommand("shutdown");
		
		jp1 = new JPanel();
		jp1.add(startServerBtn);
		jp1.add(shutDownServerBtn);
		
		this.add(jp1);
		this.setVisible(true);
		this.setTitle("服務器管理界面");
		this.setSize(500,400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	public static void main(String[] args) {
		MyServerFrame msf = new MyServerFrame();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!arg0.getActionCommand().equals("")) {
			switch(arg0.getActionCommand()) {
				case "start":
					ls = new LineServer();
					break;
				case "shutdown":
					System.out.println("shutdown comment from MyServerFrame");
					break;
			}
		}
		
	}

}
