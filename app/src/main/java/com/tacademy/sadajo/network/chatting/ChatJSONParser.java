package com.tacademy.sadajo.network.chatting;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public static ChattingList getChatListParsing(String responsedJSON) {

        ChattingList chattingList = new ChattingList(); //TODO:Json변수 수정필요
        ArrayList<ChatDataList> chatDataLists = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(responsedJSON);
            chattingList.user = jsonObject.getInt("user");

            JSONArray lists = jsonObject.getJSONArray("list");
            int arraySize = lists.length();
            for (int i = 0; i < arraySize; i++) {
               ChatDataList chatDataList = new ChatDataList();
                JSONObject  list = lists.getJSONObject(i);
                chatDataList.roomNum = list.getInt("room");
                chatDataList.requestUserCode = list.getInt("request");
                chatDataList.carrierUserCode = list.getInt("carrier");

                chatDataLists.add(chatDataList);
            }
            chattingList.chatDataList = chatDataLists;

        } catch (JSONException e) {
            Log.e("jsonParser", e.toString());
        }
        return chattingList;
    }

}