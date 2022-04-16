/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 15:40:50
 * @Description: 服务器与客户端之间通信的消息
 */
package util;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Message {
    // 服务器端与客户端的通信信息数据结构应当保持一致
    private int type;
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
    private String sender;// 发送方：自己
    private Object message;// 消息：可以是com.Message、com.FileMessage、数据库请求字符串、简单填充等
    /**
     * 规范（客户端->服务器）
     * 11,12：用户账号
     * 21,22：com.Message、com.FileMessage
     * 31,32,33,34,35：数据库请求字符串
     * 规范（服务器->客户端）
     * 11,12：用户账号
     * 21,22：com.Message、com.FileMessage
     * 31：查询结果（boolean），当前在线用户列表
     * 32,33,34：查询结果（boolean）
     * 35：查询结果（boolean），密码
     */
    private ArrayList<String> others;// 其他信息，备注等，如服务器返回的在线用户列表、用户名、密码等
    private LocalDateTime time;// 时间
    private String receiverip;// 接收方ip
    private int receiverport;// 接收方端口号

    public int getType() {
        return type;
    }

    public ArrayList<String> getOthers() {
        return others;
    }

    public void setOthers(ArrayList<String> others) {
        this.others = others;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
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

}
