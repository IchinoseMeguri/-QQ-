package util;

import java.io.*;
import java.net.*;

public class User {
	public String userName=null;
	public Socket socket=null;
	//public PrintStream out=null;
	//public BufferedReader in=null;
	public ObjectInputStream in=null;
	public ObjectOutputStream out=null;	
	public Thread serverReceiver=null;
	
	public User(){}
	public User(String userName,Socket socket){
		this.userName=userName;
		this.socket=socket;
		try {
			//this.out=new PrintStream(socket.getOutputStream());
			//this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out=new ObjectOutputStream(socket.getOutputStream());
			this.in=new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
	public User(String userName,Socket socket,ObjectInputStream in,ObjectOutputStream out){
		this.userName=userName;
		this.socket=socket;
		//this.out=new PrintStream(socket.getOutputStream());
		//this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out=out;
		this.in=in;
		this.in=in;
		this.out=out;
		//this.serverReceiver=serverReceiver;
	}
	
	public void setUsername(String username) {
		this.userName=username;
	}
	
	public String getUsername() {
		return userName;
	}
}
