package util;

import java.io.*;
import java.net.*;
import java.util.Map;

import javax.swing.JTextArea;

import gui.ServerGUI;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Server {
	// server basic
	public int serverPort = 50000;
	public ServerSocket serverSocket = null;
	ConnecterThread connecterThread = null;
	Thread connecter = null;

	// when a GUI is created, it links with this
	public ServerGUI serverGUI = null;

	/*
	 * //registered user pool
	 * public Map<String,User> userWhiteList=new HashMap<String,User>();
	 * //logined user pool
	 * public Map<String,User> users=new HashMap<String,User>();
	 */
	public ArrayList<User> users = new ArrayList<User>();

	// construction methods
	public Server() {
		startConnection();
	}

	public Server(int port) {
		serverPort = port;
		startConnection();
	}

	public boolean startConnection() {
		try {
			serverSocket = new ServerSocket(serverPort); // allocate port
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create a thread to connect all users
		connecterThread = new ConnecterThread(this);
		connecter = new Thread(connecterThread);
		connecter.start();

		println("**Server started");
		return true;
	}

	/*
	 * public boolean checkLoginedUser(String userName){ //check if this user is
	 * logined
	 * for (Map.Entry<String,User> entry : users.entrySet()) {
	 * if(entry.getKey().equals(userName)) return true;
	 * }
	 * 
	 * return false;
	 * }
	 */

	public void addUser(User user) {
		users.add(user);
		// users.put(user.userName,user);
		/*
		 * if(serverGUI!=null) {
		 * serverGUI.refreshUser();
		 * }
		 */
	}

	public ArrayList<String> getNowUsers() {
		ArrayList<String> names = new ArrayList<String>();

		for (int i = 0; i < users.size(); i++) {
			names.add(users.get(i).getUsername());
		}

		return names;
	}

	public void sendToAll(Message message) {
		for (int i = 0; i < users.size(); i++) { // 遍历整个用户列表
			// 向其他在线用户发送
			try {
				users.get(i).out.writeObject(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		println(message.message.sender + "(to all):\n" + message.message.message);
	}

	public void relay(Message message) {
		String to = message.message.receiver;
		// String to = message.getFile().getReceiver();
		User toUser = null;
		// 通过用户名搜索用户
		for (int i = 0; i < users.size(); i++) { // 遍历整个用户列表
			// 向其他在线用户发送新登录者用户名
			if (users.get(i).userName.equals(to)) {
				toUser = users.get(i);
			}
		}

		// 用户不在线
		if (toUser == null)
			return;

		// 输出信息
		try {
			toUser.out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		println(message.message.sender + "(to" + message.message.getReceiver() + "):\n" + message.message.message);
	}

	public void FilesendToAll(Message message) {
		for (int i = 0; i < users.size(); i++) { // 遍历整个用户列表
			// 向其他在线用户发送
			try {
				users.get(i).out.writeObject(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message.filemessage.isStartOfFile())
			println(message.filemessage.getSender() + "(to all):\n" + message.filemessage.getFilename());
	}

	public void Filerelay(Message message) {
		String to = message.getFile().getReceiver();
		User toUser = null;
		// 通过用户名搜索用户
		for (int i = 0; i < users.size(); i++) { // 遍历整个用户列表
			// 向其他在线用户发送新登录者用户名
			if (users.get(i).userName.equals(to)) {
				toUser = users.get(i);
			}
		}

		// 用户不在线
		if (toUser == null)
			return;

		// 输出信息
		try {
			toUser.out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (message.getFile().isStartOfFile())
			println(message.filemessage.getSender() + "(to" + message.filemessage.getReceiver() + "):\n"
					+ message.filemessage.getFilename());
	}
	/*
	 * public void sendToAll(String message) {
	 * println("--------------------------");
	 * for (Map.Entry<String,User> user : users.entrySet()) {
	 * //entry.getValue().out.println("server : "+message);
	 * try {
	 * user.getValue().out.writeObject(new
	 * Message("server",user.getValue().userName,message));
	 * } catch (IOException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * }
	 * }
	 * public void sendToAll(String from,String message) {
	 * println("--------------------------");
	 * println(new Date().toString());
	 * println("server : "+message);
	 * for (Map.Entry<String,User> user : users.entrySet()) {
	 * //entry.getValue().out.println("server : "+message);
	 * try {
	 * user.getValue().out.writeObject(new
	 * Message(from,user.getValue().userName,message));
	 * } catch (IOException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * }
	 * }
	 * 
	 * public void relay(Message message) {
	 * //user not exist
	 * if(!checkLoginedUser(message.to)) {
	 * User to=users.get(message.from);
	 * try {
	 * to.out.writeObject(new
	 * Message("server",message.from,"User "+message.to+" not exists"));
	 * } catch (IOException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * return;
	 * }
	 * 
	 * //user exist, relay
	 * User to=users.get(message.to);
	 * try {
	 * to.out.writeObject(new
	 * Message("(private message) from "+message.from,message.to,message.message));
	 * } catch (IOException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * }
	 */

	public void println(String message) {
		System.out.println(message);

		if (serverGUI != null) {
			serverGUI.logAppend(message);
		}

	}

	public boolean removeUserFromServer(String username) {
		// TODO Auto-generated method stub
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).userName.equals(username)) {
				users.remove(i);
				return true;
			}
		}

		return false;
	}

}
