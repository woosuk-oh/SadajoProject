//package com.tacademy.sadajo.chatting;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.tacademy.sadajo.R;
//
//import java.util.ArrayList;
//
//
///**
// * Created by EUNZY on 2016. 12. 2..
// */
//
//public class ChatDetailRecyclerViewAdapter extends
//        RecyclerView.Adapter<ChatDetailRecyclerViewAdapter.ViewHolder> {
//    private Context context;
//    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
//
//    public ChatDetailRecyclerViewAdapter(Context context) {
//
//        this.context = context;
//       // this.chatMessages = chatMessages;
////        Log.e("chat",chatMessages.get(0).getMessage());
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        public  View mView;
//        public  TextView mMessageView;
//
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mMessageView = (TextView) view.findViewById(R.id.chatMessage);
//
//
//        }   public void setMessage(String message) {
//            if (null == mMessageView){
//                return;
//            }else{
//                mMessageView.setText(message);
//            }
//        }
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        Log.e("type",String.valueOf(chatMessages.get(position).getType()));
//
//        return chatMessages.get(position).getType();
//    }
//
//
//    @Override
//    public ChatDetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        int layout = -1;
//
//        Log.e("type",String.valueOf(viewType));
//
//
//        switch (viewType) {
//            case ChatMessage.TYPE_LEFT:
//                layout = R.layout.chatting_message;
//                break;
//            case ChatMessage.TYPE_RIGHT:
//                layout = R.layout.chatting_message_right;
//                break;
//        }
//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                layout, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    public void onBindViewHolder(final ChatDetailRecyclerViewAdapter.ViewHolder holder, final int position) {
//
//        int viewType = getItemViewType(position);
//        Log.e("string",String.valueOf(viewType));
//        ChatMessage message = chatMessages.get(position);
//       // holder.content.setText(chatMessages.get(position).getMessage());
//        holder.setMessage(message.getMessage());
//
//    }
//
//
//    public  void add(ArrayList<ChatMessage> chatMessage){
//        this.chatMessages = chatMessage;
//
//    }
//
//    @Override
//    public int getItemCount() {
//        Log.e("itemsize", String.valueOf(chatMessages.size()));
//        return chatMessages.size();
//    }
//}
