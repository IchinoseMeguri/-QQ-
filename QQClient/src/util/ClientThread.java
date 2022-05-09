/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 15:24:49
 * @Description: 接收消息线程，并进行翻译和操作
 */
package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClientThread extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private byte[] data = new byte[8096];

    private String username;
    private gui.Login loginframe;
    private gui.Chat chatframe;
    private gui.Register registerframe;
    private gui.FindPasswd findframe;

    public ClientThread(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            while (!socket.isConnected())
                ;
            start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) in.readObject();
                switch (message.getType()) {
                    /**
                     * 消息类型
                     * 01,02：注册
                     * 11,12：登录
                     * 13：退出
                     * 31,32：找回密码
                     * 41：文字消息
                     * 42：文件消息
                     * 14：有人登陆
                     * 15：有人退出
                     */
                    case 14:
                        chatframe.NewLogin(message.getUsername());
                        break;
                    case 15:
                        chatframe.NewLogout(message.getUsername());
                        break;
                    case 41:
                        chatframe.ReceiveMessage(message.getMessage());
                        break;
                    case 42:
                        chatframe.ReceiveFile(message.getFile());
                        break;
                    case 12:
                        loginframe.LoginJudge(message.isResult(),
                                (ArrayList<String>) message.getNowUsers());
                        break;
                    case 2:
                        registerframe.RegisterOK(message.isResult());
                        break;
                    case 32:
                        findframe.Judge(message.isResult(),
                                (String) message.getPasswd());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SendToServer(Message message) {
        message.setTime(LocalDateTime.now());
        message.setReceiverip(socket.getInetAddress().getHostAddress());
        message.setReceiverport(socket.getPort());
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CloseClient() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public gui.Login getLoginframe() {
        return loginframe;
    }

    public void setLoginframe(gui.Login loginframe) {
        this.loginframe = loginframe;
    }

    public gui.Chat getChatframe() {
        return chatframe;
    }

    public void setChatframe(gui.Chat chatframe) {
        this.chatframe = chatframe;
    }

    public gui.Register getRegisterframe() {
        return registerframe;
    }

    public void setRegisterframe(gui.Register registerframe) {
        this.registerframe = registerframe;
    }

    public gui.FindPasswd getFindframe() {
        return findframe;
    }

    public void setFindframe(gui.FindPasswd findframe) {
        this.findframe = findframe;
    }

}
