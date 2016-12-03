package com.tacademy.sadajo.chatting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.sadajo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;

    public MessageAdapter(Context context, List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;

        Log.e("type",String.valueOf(viewType));
        switch (viewType) {
            case Message.TYPE_LEFT:
                layout = R.layout.chatting_message;
                break;
            case Message.TYPE_RIGHT:
                layout = R.layout.chatting_message_right;
                break;

        }
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Message message = mMessages.get(position);
        viewHolder.setMessage(message.getMessage());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        String time = dateFormat.format(date);
        viewHolder.chatTimeTextView.setText(time);
    }

    @Override
    public int getItemCount() {
        Log.e("size",String.valueOf(mMessages.size()));
        return mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessageView;
        private TextView chatTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mMessageView = (TextView) itemView.findViewById(R.id.chatMessage);
            chatTimeTextView = (TextView) itemView.findViewById(R.id.chatTimeTextView);

        }


        public void setMessage(String message) {
            if (null == mMessageView) return;
            mMessageView.setText(message);
        }



    }
}
