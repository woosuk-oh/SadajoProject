//package com.tacademy.sadajo.chatting;
//
///**
// * Created by EUNZY on 2016. 11. 28..
// */
//
//import android.app.Activity;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.tacademy.sadajo.R;
//
//import java.util.List;
//
//
//public class ChattingAdapter extends ArrayAdapter<ChatMessage> {
//
//    private Activity activity;
//    private List<ChatMessage> messages;
//
//    public ChattingAdapter(Activity context, int resource, List<ChatMessage> objects) {
//        super(context, resource, objects);
//        this.activity = context;
//        this.messages = objects;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        RecyclerView.ViewHolder holder;
//        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//
//        int layoutResource = 0; // determined by view type
//        ChatMessage chatMessage = getItem(position);
//        int viewType = getItemViewType(position);
//
//        if (chatMessage.isMine()) {
//            layoutResource = R.layout.chatting_message;
////        } else {
////            layoutResource = R.layout.item_chat_right;
////        }
//
//            if (convertView != null) {
//                holder = (RecyclerView.ViewHolder) convertView.getTag();
//            } else {
//                convertView = inflater.inflate(layoutResource, parent, false);
//                holder = new RecyclerView.ViewHolder(convertView);
//                convertView.setTag(holder);
//            }
//
//            //set message content
//            holder.msg.setText(chatMessage.getContent());
//
//            return convertView;
//        }
//
//        @Override
//        public int getViewTypeCount () {
//            // return the total number of view types. this value should never change
//            // at runtime
//            return 2;
//        }
//
//        @Override
//        public int getItemViewType ( int position){
//            // return a value between 0 and (getViewTypeCount - 1)
//            return position % 2;
//        }
//
//        private class ViewHolder {
//            private TextView msg;
//
//            public ViewHolder(View v) {
//                msg = (TextView) v.findViewById(R.id.txt_msg);
//            }
//        }
//    }
//}