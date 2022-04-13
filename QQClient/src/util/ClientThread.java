/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 15:24:49
 * @Description: 接收消息线程，并进行翻译和操作
 */
package util;

import java.net.Socket;
import java.time.LocalDateTime;

public class ClientThread extends Thread {
    private Socket socket;

    private String username;

    public ClientThread() {

    }

    @Override
    public void run() {

    }

    public void SendToServer(Object obj, int type) {
        Message message = new Message();
        message.setSender(username);
        message.setType(type);
        message.setTime(LocalDateTime.now());
        message.setMessage(obj);
        message.setReceiverip(null);// TODO
        message.setReceiverport(0);
    }

    public void ReceiveFromServer() {

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

}
