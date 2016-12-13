package com.tacademy.sadajo.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.tacademy.sadajo.R;
import com.tacademy.sadajo.chatting.ChattingDetailActivity;
import com.tacademy.sadajo.network.chatting.ChatListDB;

public class NewMessageAlertActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton messageCheckButton;
    ImageButton messageCancelButton;
    ChatListDB chatListDB;
    int roomNum;
    int receiver;
    String receiverName;
    String receiverImg;
    public static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_new_message_dialog);

        setTitle("");
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.6f;
        window.setAttributes(params);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        messageCancelButton = (ImageButton) findViewById(R.id.messageCancelButton);
        messageCheckButton = (ImageButton) findViewById(R.id.messageCheckButton);
        chatListDB = new ChatListDB();
        Intent intent = getIntent();
        roomNum = intent.getIntExtra("roomNum", 0);
        receiver = intent.getIntExtra("receiver", 0);
        receiverName = intent.getStringExtra("receiverName");
        receiverImg = intent.getStringExtra("receiverImg");

        messageCancelButton.setOnClickListener(this);
        messageCheckButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.messageCancelButton:

                finish();
                break;
            case R.id.messageCheckButton:
                Intent intent = new Intent(this, ChattingDetailActivity.class);
                intent.putExtra("roomNum", roomNum);
                intent.putExtra("receiver", receiver); //상대방
                intent.putExtra("receiverName", receiverName);
                intent.putExtra("receiverImg", receiverImg);
                intent.putExtra("type", 2);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

//                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                        PendingIntent.FLAG_ONE_SHOT);//한번 실행하면 스테이터스바에서 없어지게
                startActivity(intent);

                finish();
                break;
        }
    }
}