package com.tacademy.sadajo.chatting;

/**
 * Created by EUNZY on 2016. 11. 28..
 */

public class ChatMessage {

    public boolean type;
    public String message;

    public ChatMessage(boolean type, String message) {
        super();
        this.type = type;
        this.message = message;
    }

    public String getContent() {
        return message;
    }

    public boolean isMine() {
        return type;
    }
}