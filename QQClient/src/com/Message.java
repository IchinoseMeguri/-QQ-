/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 13:24:07
 * @Description: 信息类
 */
package com;

import java.time.LocalDateTime;

public class Message {
    private String message;
    private LocalDateTime time;
    private String sender;
    private int type;// 0：广播，1：私聊
    private String receiver;

    public String getMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
