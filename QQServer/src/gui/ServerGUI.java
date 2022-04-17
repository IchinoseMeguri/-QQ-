package gui;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import util.Server;


public class ServerGUI {
	//server
		public Server server;
		
		//frame
		public JFrame fr=new JFrame("服务器");
		//component
		public JTextArea log=new JTextArea("",20,20);
		public JScrollPane jsp=new JScrollPane(log);
		public ServerEnterTextPanel setp=new ServerEnterTextPanel(this);
		public ServerInfoPanel sip;
		
		public ServerGUI() {
			server=new Server();
			server.serverGUI=this;
			fr.add(jsp,BorderLayout.CENTER);
			fr.add(setp,BorderLayout.SOUTH);
			sip=new ServerInfoPanel(this);
			fr.add(sip,BorderLayout.WEST);
			
			//fr.setLocationRelativeTo(null);
			fr.pack();
			fr.setVisible(true);
			fr.setDefaultCloseOperation(fr.EXIT_ON_CLOSE);
			
			//startConnection();
		}
		public ServerGUI(Server server) {
			this.server=server;
			server.serverGUI=this;
			fr.add(jsp,BorderLayout.CENTER);
			fr.add(setp,BorderLayout.SOUTH);
			sip=new ServerInfoPanel(this);
			fr.add(sip,BorderLayout.WEST);
			
			//fr.setLocationRelativeTo(null);
			fr.pack();
			fr.setVisible(true);
			fr.setDefaultCloseOperation(fr.EXIT_ON_CLOSE);
			
			//startConnection();
		}
		
		public void logAppend(String message) {
			log.append(message+"\n");
		}
		
		public void refreshUser() {
			sip.loginUser.setText("已登录用户");
			/*
			for (Map.Entry<String,User> entry : server.users.entrySet()) {
				sip.loginUser.append(entry.getKey()+"\n");
			}
			*/
			for(int i=0;i<server.users.size();i++) {
				sip.loginUser.append(server.users.get(i).userName+"\n");
			}
		}
		
		
		public static void main(String args[]) {
			new ServerGUI();
		}

}
