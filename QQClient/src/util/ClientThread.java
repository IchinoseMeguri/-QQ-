/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 15:24:49
 * @Description: 接收消息线程，并进行翻译和操作
 */
package util;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDateTime;

import javax.swing.JFrame;

public class ClientThread extends Thread {
    private DatagramSocket socket;
    private byte[] data = new byte[8096];

    private String username;
    private JFrame frame;

    public ClientThread(JFrame frame, String ip, int port) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
                Message message = (Message) in.readObject();
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
                        ((gui.Chat) frame).NewLogin((String) message.getMessage());
                        break;
                    case 12:
                        ((gui.Chat) frame).NewLogout((String) message.getMessage());
                        break;
                    case 21:
                        ((gui.Chat) frame).ReceiveMessage((com.Message) message.getMessage());
                        break;
                    case 22:
                        ((gui.Chat) frame).ReceiveFile((com.FileMessage) message.getMessage());
                        break;
                    case 31:// ((gui.Login)frame).LoginJudge(b, online);break;//TODO
                    case 32:
                        ((gui.Register) frame).NameJudge((boolean) message.getMessage());
                        break;
                    case 33:
                        ((gui.Register) frame).RegisterOK((boolean) message.getMessage());
                        break;
                    case 34:
                        ((gui.Login) frame).FindJudge((boolean) message.getMessage());
                        break;
                    case 35:
                        // ((gui.FindPasswd)frame).Judge(b, passwd);break;//TODO
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SendToServer(Object obj, int type) {
        Message message = new Message();
        message.setSender(username);
        message.setType(type);
        message.setTime(LocalDateTime.now());
        message.setMessage(obj);
        message.setReceiverip(socket.getInetAddress().getHostAddress());// TODO
        message.setReceiverport(socket.getPort());
    }

    public void CloseClient() {
        socket.close();
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
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
