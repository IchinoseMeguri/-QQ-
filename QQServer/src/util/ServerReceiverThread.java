package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import gui.ServerGUI;

//monitor user to Server
public class ServerReceiverThread implements Runnable {
	public Server server;
	public User user;
	Message message=null;
	
	public ServerReceiverThread(Server server,User user) {
		this.server=server;
		this.user=user;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!user.socket.isClosed()){
			message=null;
			//read message
			try {
				//message=user.in.readLine();
				message=(Message)user.in.readObject();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch (message.getType()) {
	        
	        case 41:
	            receiveTextMessage();
	            break;
	        case 13:
	            logout();
	            break;
	        
	        default:
	        	;
			}
			
			logout();
			
			/*
			if(message!=null){
				server.println("--------------------------");
				server.println(new Date().toString());
				server.println(message.from+" : "+message.message);
			}
			else {
				//server.println("**"+message.message);
				server.println("**reading message error");
				return;
			}
			
			//logout protocol
			if(message.message.equals("cmd|logout "+user.userName)) {
				server.println("**server is disconnecting with client "+user.userName);
				server.println("cmd|close "+user.userName);
				//user.out.println("cmd|close "+user.userName);
				try {
					user.out.writeObject(new Message("server",user.userName,"cmd|close "+user.userName));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					user.socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				server.users.remove(user.userName);
				server.serverGUI.refreshUser();
				user.serverReceiver.stop();
				return;
			}
			
			//relay protocol
			if(message.to.equals("all")) {
				server.sendToAll(message.from,message.message);
			}
			else {
				server.relay(message);
			}
			*/
			
		}
	}
	
	public boolean receiveTextMessage() {
		/*/防乱写用户名
		if(message.message.sender!=user.userName) {
			message.message.sender=user.userName;
		}
		/*/
		
		if(message.message.type==0) {	//广播
			server.sendToAll(message);
			//server.println(message.message.sender+"(to all):\n"+message.message.message);
		}
		else {
			server.relay(message);
			//server.println(message.message.sender+"(to "+message.message.receiver+"):\n"+message.message.message);
		}
		
		return true;
	}
	
	public boolean logout() {
		// TODO Auto-generated method stub

		//delete user from server
		server.removeUserFromServer(message.getUsername());
		server.println(message.getUsername()+"已退出");
		
		Message outUserName = new Message();
		outUserName.setUsername(message.Username);
		outUserName.setType(15);
		// 向所有其他在线用户发送退出用户名
		for (int i = 0; i < server.users.size(); i++) { // 遍历整个用户列表
			// 向其他在线用户发送退出用户名
			try {
				server.users.get(i).out.writeObject(outUserName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}

}
