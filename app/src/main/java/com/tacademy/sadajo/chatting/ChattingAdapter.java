package com.tacademy.sadajo.chatting;

/**
 * Created by EUNZY on 2016. 11. 28..
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tacademy.sadajo.R;

import java.util.ArrayList;
import java.util.List;


class ChattingAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    private Context context;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChattingAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessageObj = getItem(position);
        View row= convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (!chatMessageObj.isMine()) {
            row = inflater.inflate(R.layout.chatting_message, parent, false);
        }else{

            row = inflater.inflate(R.layout.chatting_message_right, parent, false);
        }
        chatText = (TextView) row.findViewById(R.id.chatMessage);
        chatText.setText(chatMessageObj.getContent());
        return row;
    }
}