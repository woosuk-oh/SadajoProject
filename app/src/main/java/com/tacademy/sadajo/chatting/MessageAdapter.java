package com.tacademy.sadajo.chatting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.sadajo.R;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> mMessages;

    public MessageAdapter(Context context, List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;


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
//        SimpleDateFormat dateFormat = new SimpleDateFormat("aa hh:mm ", Locale.getDefault());
//        //Date date = message.getTime();
//        String time = dateFormat.format(message.getTime());
        viewHolder.chatTimeTextView.setText(message.getTime());
    }

    @Override
    public int getItemCount() {
       // Log.e("size",String.valueOf(mMessages.size()));
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
