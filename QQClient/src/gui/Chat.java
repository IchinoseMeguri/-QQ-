/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-09 15:56:49
 * @Description: 聊天窗口GUI
 */
package gui;

import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.FileMessage;
import com.Message;

import util.ClientThread;

import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Chat extends JFrame {
    private JPanel left;
    private JPanel right;
    private JPanel _message;
    private JPanel _speak;
    private JPanel buttons;

    private JTextArea message;
    private JTextArea speak;
    private JScrollPane messageScrollPane;
    private JScrollPane speakScrollPane;

    private JTextArea users;
    private JScrollPane usersScrollPane;

    private JButton send;
    private JButton sendfile;
    private JComboBox<String> sendto;

    private String name;
    private ArrayList<String> nowusers = new ArrayList<String>();

    private ClientThread clientthread;

    public Chat(String name, ArrayList<String> online) {
        this.name = name;
        setTitle("聊天：" + this.name);

        left = new JPanel();
        right = new JPanel();
        _message = new JPanel();
        _speak = new JPanel();
        buttons = new JPanel();
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        left.setLayout(new BorderLayout());
        left.add(_message, BorderLayout.NORTH);
        left.add(_speak, BorderLayout.CENTER);
        left.add(buttons, BorderLayout.SOUTH);

        message = new JTextArea("", 15, 30);
        speak = new JTextArea("", 2, 30);
        messageScrollPane = new JScrollPane(message);
        speakScrollPane = new JScrollPane(speak);
        _message.setLayout(new BorderLayout());
        _speak.setLayout(new BorderLayout());
        _message.add(new JLabel("会话消息窗口"), BorderLayout.NORTH);
        _message.add(messageScrollPane, BorderLayout.CENTER);
        _speak.add(new JLabel("发言窗口"), BorderLayout.NORTH);
        _speak.add(speakScrollPane, BorderLayout.CENTER);

        send = new JButton("发送消息");
        sendfile = new JButton("发送文件");

        buttons.add(new JLabel("发送给"));
        sendto = new JComboBox<String>();
        buttons.add(sendto);
        buttons.add(send);
        buttons.add(sendfile);

        right.setLayout(new BorderLayout());
        users = new JTextArea("", 20, 10);
        usersScrollPane = new JScrollPane(users);
        right.add(new JLabel("在线用户"), BorderLayout.NORTH);
        right.add(usersScrollPane, BorderLayout.CENTER);

        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SendMessage();
            }
        });
        sendfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SendFile();
            }
        });

        this.nowusers = online;
        RefreshUsers();

        // 窗口自适应
        pack();
        // 固定可视化界面窗口大小
        setResizable(false);
        // 窗口居中
        setLocationRelativeTo(null);

    }

    public ClientThread getClientthread() {
        return clientthread;
    }

    public void setClientthread(ClientThread clientthread) {
        this.clientthread = clientthread;
    }

    private void SendFile() {
        JFileChooser select = new JFileChooser(".");
        select.setFileSelectionMode(JFileChooser.FILES_ONLY);
        select.setMultiSelectionEnabled(false);
        if (select.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = new File(select.getSelectedFile().getPath());
            FileMessage message = new FileMessage();
            message.setFile(file);
            message.setReceiver(this.sendto.getSelectedItem().toString());
            message.setSender(this.name);
            message.setTime(LocalDateTime.now());
            message.setType(this.sendto.getSelectedItem().toString() == "所有人" ? 0 : 1);
            util.Message mes = new util.Message();
            mes.setType(22);
            mes.setUsername(name);
            mes.setFile(message);
            clientthread.SendToServer(mes);
            this.speak.setText("");
        }
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            Logout();
            clientthread.CloseClient();
            System.exit(0);
        }
    }

    /**
     * @description: 当收到文本消息时，服务器端向客户端发送信号，调用此方法
     * @param sender  发送者
     * @param message 消息
     * @param type    类型（0：广播，1：私聊）
     * @return
     */
    public void ReceiveMessage(Message message) {
        String str = message.getType() == 0 ? "[广播]" : "[私聊]";
        str += "[" + message.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "]";
        str += message.getSender() + ":\n";
        str += "        " + message.getMessage() + "\n";
        this.message.append(str);
        this.message.setCaretPosition(this.message.getDocument().getLength());
    }

    public void ReceiveFile(FileMessage file) {
        String str = file.getType() == 0 ? "[广播]" : "[私聊]";
        str += "[" + file.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "]";
        str += file.getSender() + "发送了一份文件。" + "\n";
        this.message.append(str);
        this.message.setCaretPosition(this.message.getDocument().getLength());
        JFileChooser save = new JFileChooser(".");
        save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        save.setMultiSelectionEnabled(false);
        if (save.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String path = save.getSelectedFile().getPath();
            try {
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file.getFile()));
                if (!path.endsWith("\\")) {
                    path += "\\";
                }
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(path + file.getFile().getName()));
                byte[] buf = new byte[4096];
                int length = bis.read(buf);
                // 保存文件
                while (length != -1) {
                    bos.write(buf, 0, length);
                    length = bis.read(buf);
                }
                bos.close();
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 当有用户登录服务器时，服务器端向所有客户端发送信号，调用该方法，刷新聊天界面
     * @param user
     * @return
     */
    public void NewLogin(String user) {
        nowusers.add(user);
        RefreshUsers();
    }

    /**
     * @description: 告知服务器用户退出服务器
     * @param
     * @return
     */
    private void Logout() {
        util.Message message = new util.Message();
        message.setType(12);
        message.setUsername(name);
        clientthread.SendToServer(message);
    }

    private void SendMessage() {
        Message message = new Message();
        message.setMessage(this.speak.getText());
        message.setReceiver(this.sendto.getSelectedItem().toString());
        message.setSender(this.name);
        message.setTime(LocalDateTime.now());
        message.setType(this.sendto.getSelectedItem().toString() == "所有人" ? 0 : 1);
        util.Message mes = new util.Message();
        mes.setType(21);
        mes.setUsername(name);
        mes.setMessage(message);
        clientthread.SendToServer(mes);
        this.speak.setText("");
    }

    /**
     * @description: 当有用户登出服务器时，服务器端向所有客户端发送信号，调用该方法，刷新聊天界面
     * @param user
     * @return
     */
    public void NewLogout(String user) {
        nowusers.remove(user);
        RefreshUsers();
    }

    private void RefreshUsers() {
        sendto.removeAllItems();
        sendto.addItem("所有人");
        this.users.setText("");
        for (String string : nowusers) {
            this.users.append(string);
            sendto.addItem(string);
        }
    }
}
