package util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    // 服务器端与客户端的通信信息数据结构应当保持一致
    public int type;

    public String Username;// 用户账号
    public String Passwd;// 密码
    public String Phone;// 手机号

    public ArrayList<String> nowUsers;// 当前在线用户列表
    public boolean result;// 操作结果

    public com.Message message;// 消息
    public com.FileMessage filemessage;// 文件

    public LocalDateTime time;// 时间
    public String receiverip;// 接收方ip
    public int receiverport;// 接收方端口号

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getReceiverip() {
        return receiverip;
    }

    public void setReceiverip(String receiverip) {
        this.receiverip = receiverip;
    }

    public int getReceiverport() {
        return receiverport;
    }

    public void setReceiverport(int receiverport) {
        this.receiverport = receiverport;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPasswd() {
        return Passwd;
    }

    public void setPasswd(String passwd) {
        Passwd = passwd;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public ArrayList<String> getNowUsers() {
        return nowUsers;
    }

    public void setNowUsers(ArrayList<String> nowUsers) {
        this.nowUsers = nowUsers;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public com.Message getMessage() {
        return message;
    }

    public void setMessage(com.Message message) {
        this.message = message;
    }

    public com.FileMessage getFile() {
        return filemessage;
    }

    public void setFile(com.FileMessage file) {
        this.filemessage = file;
    }

}
