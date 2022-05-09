package util;

import java.io.*;
import java.net.*;

//build a user for Server, or decline illegal client connection
public class ConnecterThread implements Runnable {
	public Server server = null;
	// current user info -> User type
	public String userName = null;
	public Socket socket = null;
	// public PrintStream out=null;
	// public BufferedReader in=null;
	// public String message=null;
	public ObjectOutputStream out = null;
	public ObjectInputStream in = null;
	public Message message = null;

	public ConnecterThread(Server server) {
		this.server = server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			// connect to client
			startConnection();

			// build tmp communication channel
			buildCommunicationChannel();

			cmdProtocol();

			// createUserToServer();

		}
	}

	public boolean startConnection() {
		socket = new Socket();
		println("**Server is ready to connect to Client");
		try {
			socket = server.serverSocket.accept(); // wait for connection
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (socket.isConnected()) {
			println("**Server is connected to Client");
			return true;
		} else {
			println("**Server connection failure");
			return false;
		}

	}

	// build tmp Communication Channel, for login certification
	public boolean buildCommunicationChannel() {
		try {
			// out=new PrintStream(socket.getOutputStream());
			// in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			println("**Communication channel build failure");
			e.printStackTrace();
			return false;
		}

		println("**Communication channel build success");
		return true;
	}

	public boolean cmdProtocol() {
		println("**ready to excute cmd message");

		message = null;
		// receive cmd message
		try {
			// message=in.readLine();
			message = (Message) in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			server.println(Integer.toString(message.getType()));
			return false;
		}

		switch (message.getType()) {

			case 01:
				// ((gui.Chat) frame).NewLogin(message.getUsername());
				register();
				break;
			case 11:
				// ((gui.Chat) frame).NewLogout(message.getUsername());
				try {
					login();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 31:
				// ((gui.Chat) frame).ReceiveMessage(message.getMessage());
				findPasswd();
				break;
			default:
				return false;
		}

		return true;
	}

	/*
	 * public boolean loginProtocol() {
	 * println("**ready to have client login");
	 * //ask for userName
	 * //out.println("cmd|userName?");
	 * try {
	 * out.writeObject(new Message("server","tmp user","cmd|userName?"));
	 * } catch (IOException e1) {
	 * // TODO Auto-generated catch block
	 * e1.printStackTrace();
	 * } //sending in this stream, and it only goes to one destination
	 * //receive userName
	 * try {
	 * //message=in.readLine();
	 * message=(Message)in.readObject();
	 * 
	 * } catch (Exception e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * return false;
	 * }
	 * 
	 * //check format
	 * if(!message.message.startsWith("cmd|")){
	 * println("**client respond format error");
	 * failedLogin();
	 * return false;
	 * }
	 * userName=message.from;
	 * println("**current login userName is "+userName);
	 * 
	 * //login certification
	 * if(socket.isConnected()&&!server.checkLoginedUser(userName)) {
	 * println("**"+userName+" login successfully");
	 * //out.println("cmd|welcome");
	 * try {
	 * out.writeObject(new Message("server",userName,"cmd|welcome"));
	 * } catch (IOException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * return true;
	 * }
	 * else {
	 * println("**"+userName+" login failure");
	 * failedLogin();
	 * return false;
	 * }
	 * }
	 */

	/*
	 * public void failedLogin(){
	 * //out.println("cmd|reject");
	 * try {
	 * out.writeObject(new Message("server",userName,"cmd|reject"));
	 * } catch (IOException e1) {
	 * // TODO Auto-generated catch block
	 * e1.printStackTrace();
	 * }
	 * 
	 * try {
	 * socket.close();
	 * } catch (IOException e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * }
	 */

	private void register() {
		// TODO Auto-generated method stub
		message.setType(02);
		if (SqlHelper.register(message.getUsername(), message.getPasswd(), message.getPhone()) >= 1) {
			message.setResult(true);
		} else {
			message.setResult(false);
		}

		try {
			out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void login() throws IOException {
		// TODO Auto-generated method stub
		message.setType(12);
		int n = SqlHelper.Login(message.getUsername(), message.getPasswd());
		if (n <= 0) {// 登录失败
			message.setResult(false);
			out.writeObject(message);
		} else { // 登录成功
			message.setResult(true);
			// nowserver.users
			message.setNowUsers(server.getNowUsers());
			out.writeObject(message);

			Message newUserName = new Message();
			newUserName.setUsername(message.Username);
			newUserName.setType(14);
			// 向所有其他在线用户发送新登录者用户名
			for (int i = 0; i < server.users.size(); i++) { // 遍历整个用户列表
				// 向其他在线用户发送新登录者用户名
				server.users.get(i).out.writeObject(newUserName);
			}

			// init user to server
			createUserToServer(message.getUsername());
			server.println(message.getUsername() + "已登录");

		}
	}

	private void findPasswd() {
		// TODO Auto-generated method stub
		message.setType(32);
		if (SqlHelper.findPasswd(message.getUsername(), message.getPasswd(), message.getPhone()) >= 1) {
			message.setResult(true);
		} else {
			message.setResult(false);
		}

		try {
			out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean createUserToServer(String userName) {
		User user = new User(userName, socket, in, out);
		server.addUser(user);

		Thread receiver = new Thread(new ServerReceiverThread(server, user));
		user.serverReceiver = receiver;
		receiver.start();

		return true;
	}

	public void println(String message) {
		System.out.println(message);
	}

}
