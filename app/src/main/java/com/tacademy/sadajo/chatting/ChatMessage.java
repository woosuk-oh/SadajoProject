package com.tacademy.sadajo.chatting;

/**
 * Created by EUNZY on 2016. 11. 28..
 */

public class ChatMessage {

    public boolean left;
    public String message;

    public ChatMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }

    public String getContent() {
        return message;
    }

    public boolean isMine() {
        return left;
    }
}