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

import javax.swing.JFrame;

public class ClientThread extends Thread {
    private Socket socket;
    private byte[] data = new byte[8096];

    private String username;
    private JFrame frame;

    public ClientThread(JFrame frame, String ip, int port) {
        try {
            socket = new Socket(ip, port);
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
                ObjectInputStream out = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) out.readObject();
                switch (message.getType()) {
                    /**
                     * 消息类型
                     * 11：用户登录
                     * 12：用户退出
                     * 21：发送消息
                     * 22：发送文件
                     * 31：登录认证（数据库请求）
                     * 32：注册时重名查询（数据库请求）
                     * 33：注册时添加账户（数据库请求）
                     * 34：找回密码时查找账户（数据库请求）
                     * 35：找回密码时验证密保问题（数据库请求）
                     */
                    case 11:
                        ((gui.Chat) frame).NewLogin(message.getUsername());
                        break;
                    case 12:
                        ((gui.Chat) frame).NewLogout(message.getUsername());
                        break;
                    case 21:
                        ((gui.Chat) frame).ReceiveMessage(message.getMessage());
                        break;
                    case 22:
                        ((gui.Chat) frame).ReceiveFile(message.getFile());
                        break;
                    case 31:
                        ((gui.Login) frame).LoginJudge(message.isResult(),
                                (ArrayList<String>) message.getNowUsers());
                        break;
                    case 32:
                        ((gui.Register) frame).NameJudge(message.isResult());
                        break;
                    case 33:
                        ((gui.Register) frame).RegisterOK(message.isResult());
                        break;
                    case 34:
                    case 35:
                        ((gui.FindPasswd) frame).Judge(message.isResult(),
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
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
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

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

}
