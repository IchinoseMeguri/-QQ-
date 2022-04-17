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
     * 35：找回密码时验证手机号（数据库请求）
     */
    private String Username;// 用户账号
    private String Passwd;// 密码
    private String Phone;// 手机号

    private ArrayList<String> nowUsers;// 当前在线用户列表
    private boolean result;// 数据库操作结果

    private com.Message message;// 消息
    private com.FileMessage filemessage;// 文件

    private LocalDateTime time;// 时间
    private String receiverip;// 接收方ip
    private int receiverport;// 接收方端口号

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

    public String getAnswer() {
        return Phone;
    }

    public void setAnswer(String answer) {
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
