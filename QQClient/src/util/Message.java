/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 15:40:50
 * @Description: 服务器与客户端之间通信的消息
 */
package util;

import java.time.LocalDateTime;

public class Message {
    // 服务器端与客户端的通信信息数据结构应当保持一致
    private int type;
    /**
     * 消息类型
     * 1：用户登录
     * 2：用户退出
     * 3：发送消息
     * 4：发送文件
     * 5：数据库请求
     */
    private String sender;// 发送方：自己
    private Object message;// 消息：com.Message或com.FileMessage
    private LocalDateTime time;// 时间
    private String receiverip;// 接收方ip
    private int receiverport;// 接收方端口号

    public int getType() {
        return type;
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
