package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerInfoPanel extends JPanel {
	public JPanel line1=new JPanel(),
				  line2=new JPanel(),
				  line3=new JPanel();
	public JTextArea loginUser=new JTextArea("已登录用户\n",10,10);
	public JLabel startState=new JLabel("服务器已启动");
	public JButton serverStart=new JButton("启动");
	
	public ServerInfoPanel(ServerGUI serverGUI) {
		setLayout(new GridLayout(3,1));
		line1.add(new JLabel("端口"));
		line1.add(new JLabel(""+serverGUI.server.serverPort));
		
		loginUser.setEditable(false);
		line2.add(loginUser);
		
		line3.add(startState);
		serverStart.addMouseListener(new ServerStartButtonListener(serverGUI));
		line3.add(serverStart);
		
		add(line1);
		add(line2);
		add(line3);
	}
}

class ServerStartButtonListener extends MouseAdapter implements MouseListener {
	ServerGUI serverGUI;
	
	public ServerStartButtonListener(ServerGUI serverGUI){
		this.serverGUI=serverGUI;
	}
	
	public void mousePressed(MouseEvent e) {
		if(serverGUI.sip.startState.getText().equals("服务器已启动")) {
			serverGUI.server.println("**Server has already started");
			return;
		}
		
		serverGUI.sip.startState.setText("服务器已启动");
		serverGUI.server.startConnection();
	}
}