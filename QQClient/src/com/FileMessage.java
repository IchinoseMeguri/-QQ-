/*
 * @Author: Meguri Ichinose
 * @Date: 2022-04-10 14:05:15
 * @Description: 文件消息类
 */
package com;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FileMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String Filename;
    private byte str[] = new byte[8096];
    private boolean EndOfFile;
    private boolean StartOfFile;
    private LocalDateTime time;
    private String sender;
    private int type;// 0：广播，1：私聊
    private String receiver;

    public String getFilename() {
        return Filename;
    }

    public boolean isStartOfFile() {
        return StartOfFile;
    }

    public void setStartOfFile(boolean startOfFile) {
        this.StartOfFile = startOfFile;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public byte[] getStr() {
        return str;
    }

    public void setStr(byte[] str) {
        this.str = str;
    }

    public boolean isEndOfFile() {
        return EndOfFile;
    }

    public void setEndOfFile(boolean endOfFile) {
        EndOfFile = endOfFile;
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
