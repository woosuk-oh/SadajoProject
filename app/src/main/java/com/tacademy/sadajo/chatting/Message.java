package com.tacademy.sadajo.chatting;

/**
 * Created by EUNZY on 2016. 12. 2..
 */

public class Message {

    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;
//    public static final int TYPE_LOG = 1;
//    public static final int TYPE_ACTION = 2;

    private int mType;
    private String mMessage;
    private int mUsername;
    private  String mTime;

    private Message() {}

    public int getType() {
        return mType;
    };

    public String getMessage() {
        return mMessage;
    };

    public int getUsername() {
        return mUsername;
    };

    public String getTime() {
        return mTime;
    };


    public static class Builder {
        private final int mType;
        private int mUsername;
        private String mMessage;
        private  String mTime;

        public Builder(int type) {
            mType = type;
        }

        public Builder username(int username) {
            mUsername = username;
            return this;
        }

        public Builder message(String message) {
            mMessage = message;
            return this;
        }

        public Builder time(String time) {
            mTime = time;
            return this;
        }

        public Message build() {
            Message message = new Message();
            message.mType = mType;
            message.mUsername = mUsername;
            message.mMessage = mMessage;
            message.mTime = mTime;
            return message;
        }
    }
}