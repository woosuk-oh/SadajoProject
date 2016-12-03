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
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChattingDetailActivity extends BaseActivity {

    Toolbar toolbar;
    ImageButton requestButton;

    int roomNum; //방번호
    int sender;//senderID
    int receiver;//receiverID;

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List mMessages = new ArrayList<Message>();
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


        Intent intent = getIntent();
        roomNum = intent.getIntExtra("roomNum",0);
        sender = intent.getIntExtra("sender",0);
        receiver = intent.getIntExtra("receiver",0);


        Log.e("getIntentRoomNum", String.valueOf(roomNum));
        Log.e("getIntentsender", String.valueOf(sender));
        Log.e("getIntentreceiver ", String.valueOf(receiver ));


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
            object.put("user", sender);
            //perform the sending message attempt.

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
        mMessages.add(new Message.Builder(Message.TYPE_RIGHT)
                .username(username).message(message).build());
        Log.e("right", String.valueOf(username));
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void addMessageLeft(int username, String message) {

        if (username != sender) {
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
        addMessage(sender, message);

        JSONObject object = new JSONObject();
        try {
            object.put("sender", sender);
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


}
