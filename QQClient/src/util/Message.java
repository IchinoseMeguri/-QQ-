/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 15:40:50
 * @Description: 服务器与客户端之间通信的消息
 */
package util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Message implements Serializable {
    // 服务器端与客户端的通信信息数据结构应当保持一致
    public int type;
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
    public String Username;// 用户账号
    public String Passwd;// 密码
    public String Phone;// 手机号

    public ArrayList<String> nowUsers;// 当前在线用户列表
    public boolean result;// 数据库操作结果

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

    public void setPhone(String answer) {
        Phone = answer;
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
