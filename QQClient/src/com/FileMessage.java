/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 14:05:15
 * @Description: 文件消息类
 */
package com;

import java.io.File;
import java.time.LocalDateTime;

public class FileMessage {
    private File file;
    private LocalDateTime time;
    private String sender;
    private int type;// 0：广播，1：私聊
    private String receiver;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
