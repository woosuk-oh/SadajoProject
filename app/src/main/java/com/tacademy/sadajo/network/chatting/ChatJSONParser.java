package com.tacademy.sadajo.network.chatting;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by EUNZY on 2016. 12. 2..
 */

public class ChatJSONParser {
    public static ChatListDB getChaRoomParsing(String responsedJSON) {

        ChatListDB chatListDBs = new ChatListDB();

        try {
            JSONObject chat = new JSONObject(responsedJSON);


            chatListDBs.senderCode = chat.getInt("user");
            chatListDBs.receiverCode = chat.getInt("partner");
            chatListDBs.roomNum = chat.getInt("room");
            chatListDBs.img = chat.getString("img");




        } catch (JSONException e) {
            Log.e("jsonParser", e.toString());
        }
        return chatListDBs;
    }


}