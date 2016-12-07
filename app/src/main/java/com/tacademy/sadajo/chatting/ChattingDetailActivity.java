package com.tacademy.sadajo.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChattingDetailActivity extends BaseActivity {

    Toolbar toolbar;
    ImageButton requestButton;


    //OtherMyPageActivity에서 넘어 온 데이터
    int roomNum; //방번호
    int sender;//senderID
    int receiver;//receiverID;


    String conUserImg;
    String conUserName;

    private int userAccount;

    private boolean type = true;

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private ImageView conUserImageView;
    private TextView contUserNameTextView;
    private TextView conPositionTextView;

    private List<Message> mMessages = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    private Socket mSocket;


    private Boolean isConnected = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        setToolbar(true);


        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil();
        userAccount = sharedPreferenceUtil.getSharedPreference(this, "userAccount");


        getTypeIntent(); //itent로 넘어 온 데이터들


        conUserImageView = (ImageView) findViewById(R.id.conUserImageView);
        contUserNameTextView = (TextView) findViewById(R.id.contUserNameTextView);
        conPositionTextView = (TextView) findViewById(R.id.conPositionTextView);


        Glide.with(SadajoContext.getContext())
                .load(conUserImg)
                .into(conUserImageView);

        contUserNameTextView.setText(conUserName);


        SadajoContext app = (SadajoContext) getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("toClient", toClient);


        mSocket.connect();
        JSONObject object = new JSONObject();
        try {
            object.put("room", roomNum);
            object.put("user", userAccount); //본인 아이디
            //perform the sending message attempt.


            Log.e("Chatting User sender", String.valueOf(userAccount));
            Log.e("Chatting roomnum", String.valueOf(roomNum));
            mSocket.emit("joinRoom", object);
        } catch (JSONException e) {
            Log.d("SEND MESSAGE", "ERROR");
            e.printStackTrace();
        }


        mAdapter = new MessageAdapter(ChattingDetailActivity.this, mMessages);
        mMessagesView = (RecyclerView) findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) findViewById(R.id.chattingEditText);
        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send || id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    try {
                        attemptSend();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        ImageButton sendButton = (ImageButton) findViewById(R.id.chatDetailSendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attemptSend();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        requestButton = (ImageButton) findViewById(R.id.requestButton);//사다조 요청하기 버튼
        requestButton.setOnClickListener(clickListener);


    }


    private void addMessage(int username, String message) {
        if (username == userAccount) {
            mMessages.add(new Message.Builder(Message.TYPE_RIGHT)
                    .username(username).message(message).build());
            Log.e("right", String.valueOf(username));
            mAdapter.notifyItemInserted(mMessages.size() - 1);
            scrollToBottom();
        }
    }

    private void addMessageLeft(int username, String message) {

        if (username != userAccount) {
            mMessages.add(new Message.Builder(Message.TYPE_LEFT)
                    .username(username).message(message).build());
            Log.e("left", String.valueOf(username));

            mAdapter.notifyItemInserted(mMessages.size() - 1);
            scrollToBottom();
        }
    }

    private void attemptSend() throws JSONException {
        // if (null ==mUsername) return;
        if (!mSocket.connected()) return;


        String message = mInputMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }

        mInputMessageView.setText("");
        addMessage(userAccount, message);

        JSONObject object = new JSONObject();
        try {
            object.put("sender", userAccount);
            object.put("msg", message);
            //perform the sending message attempt.

            mSocket.emit("toServer", object);
        } catch (JSONException e) {
            Log.d("SEND MESSAGE", "ERROR");
            e.printStackTrace();
        }
    }


    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            (ChattingDetailActivity.this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isConnected) {
                        Toast.makeText(getApplicationContext(),
                                "연결", Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                    Log.e("connect", "success");

                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            (ChattingDetailActivity.this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = false;
                    Toast.makeText(getApplicationContext(),
                            "disconnect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            (ChattingDetailActivity.this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "error_connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };


    //메세지 받아오는 부분
    private Emitter.Listener toClient = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            (ChattingDetailActivity.this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int username;
                    String message;
                    try {
                        Log.e("to client sender", String.valueOf(data.getInt("sender")));
                        username = data.getInt("sender");
                        message = data.getString("msg");


                    } catch (JSONException e) {
                        return;
                    }


                    //  removeTyping(username);
                    addMessageLeft(username, message);
                }
            });
        }
    };
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.requestButton:
                    RequestDialogFragment dialog = new RequestDialogFragment();
                    dialog.show(getFragmentManager(), "requestDialog");
                    break;
                case R.id.chatDetailSendButton:
                    break;

            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;//false하면 메뉴아이콘 hidden
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setToolbar(boolean b) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(b); //back icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //클릭시 뒤로가기
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    public void getTypeIntent() {
        Intent intent = getIntent();
        type = intent.getBooleanExtra("type", true);
        if (type == true) { //bottom navigation으로 이동한 것이 아닌 경우
            roomNum = intent.getIntExtra("roomNum", 0);
            conUserImg = intent.getExtras().getString("conUserImg"); //상대방 이미지
            conUserName = intent.getExtras().getString("conUserName"); //상대방 이름


        } else {//bottom navigation으로 이동한 것이 아닌 경우

            // OtherMypage에서 넘어온 데이터들
            roomNum = intent.getIntExtra("roomNum", 0);
            sender = intent.getIntExtra("sender", 0);
            receiver = intent.getIntExtra("receiver", 0);
            conUserName = intent.getStringExtra("receiverName");
            conUserImg = intent.getStringExtra("receiverImg");

        }

    }

}
