package gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.*;


public class ServerEnterTextPanel extends JPanel {
	public JTextField txt=new JTextField("",20);
	public JButton button=new JButton("发送");
	
	//public ServerGUI serverGUI;	//Pass through to Listener
	
	public ServerEnterTextPanel(ServerGUI serverGUI){
		//this.serverGUI=serverGUI;
		button.addMouseListener(new ServerEnterTextButtonListener(serverGUI));
		button.setPreferredSize(new Dimension(100,25));
		
		add(txt);
		add(button);
	}
}

class ServerEnterTextButtonListener extends MouseAdapter implements MouseListener {
	ServerGUI serverGUI;
	
	public ServerEnterTextButtonListener(ServerGUI serverGUI){
		this.serverGUI=serverGUI;
	}
	
	public void mousePressed(MouseEvent e) {
		String message=serverGUI.setp.txt.getText();
		util.Message m=new util.Message();
		com.Message mm=new com.Message();
		mm.setSender("Server");
		mm.setMessage(message);
		mm.setTime(LocalDateTime.now());
		mm.setType(0);
		m.setMessage(mm);
		serverGUI.server.sendToAll(m);
		
	}
}
