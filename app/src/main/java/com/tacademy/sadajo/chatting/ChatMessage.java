package com.tacademy.sadajo.chatting;

/**
 * Created by EUNZY on 2016. 11. 28..
 */

public class ChatMessage {

    private String content;
    private boolean isMine;

    public ChatMessage( boolean isMine,String content) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}