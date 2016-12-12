package com.tacademy.sadajo.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;
import com.tacademy.sadajo.SadajoContext;
import com.tacademy.sadajo.SharedPreferenceUtil;
import com.tacademy.sadajo.dialog.NewMessageAlertActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ChattingDetailActivity extends BaseActivity {

    Toolbar toolbar;
    ImageButton requestButton;


    //OtherMyPageActivity에서 넘어 온 데이터
    int roomNum; //방번호
    String targetUserImg; //상대방이미지
    String targetUserName; //상대방이름
    int targetUserCode; //상대방유저코드

    private int userAccount; //본인유저코드

    private int type;
    private int BOTTOMBAR = 0;
    private int MYPAGE = 1;
    private int PUSH = 2;


    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private ImageView targetUserImageView;
    private TextView targetUserNameTextView;
    private ImageButton chatAttachButton;
    //private TextView conPositionTextView;

    private List<Message> mMessages = new ArrayList<>();
    private ArrayList<MsgDBEntity> pastMessages = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    private Socket mSocket;
    private Boolean isConnected = true;
    Boolean isAleady = false;
    MsgDB dbHelper;
    int i = 0;

    SharedPreferenceUtil sharedPreferenceUtil;

    private LinearLayout chatDetailTop;
    private Animation inAnim;
    private Animation outAnim;

    String temp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        setToolbar(true);

        chatDetailTop = (LinearLayout)findViewById(R.id.chatDetailTop);
        inAnim = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_top);
        outAnim = AnimationUtils.loadAnimation(this, R.anim.abc_slide_out_top);

        getTypeIntent(); //intent로 넘어 온 데이터들 받아옴

        //MsgDB
        dbHelper = new MsgDB(getApplicationContext(), "MessageHistory", null, 1);


        sharedPreferenceUtil = new SharedPreferenceUtil(this);
        userAccount = sharedPreferenceUtil.getAccessToken();


        targetUserImageView = (ImageView) findViewById(R.id.conUserImageView);
        targetUserNameTextView = (TextView) findViewById(R.id.contUserNameTextView);
        //conPositionTextView = (TextView) findViewById(conPositionTextView);


        Glide.with(SadajoContext.getContext())
                .load(targetUserImg)

                .into(targetUserImageView);

        targetUserNameTextView.setText(targetUserName);


        mAdapter = new MessageAdapter(ChattingDetailActivity.this, mMessages);
        mMessagesView = (RecyclerView) findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesView.setAdapter(mAdapter);



        //socket연결
        SadajoContext app = (SadajoContext) getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.on("toClient", toClient);

        mSocket.connect();

        joinRoom();  //채팅방 입장


        //지난 메세지 출력
        if (dbHelper.getResult(roomNum).size() > 0 && dbHelper.getResult(roomNum) != null) {
            pastMessages = dbHelper.getResult(roomNum);

            for (int i = 0; i < pastMessages.size(); i++) {

               // Log.e("messge+" + pastMessages.get(i).id, pastMessages.get(i).message);
                if (pastMessages.get(i).user == userAccount) {

                    mMessages.add(new Message.Builder(Message.TYPE_RIGHT)
                            .username(pastMessages.get(i).user).message(pastMessages.get(i).message).build());
                    mAdapter.notifyItemInserted(mMessages.size() - 1);
                    scrollToBottom();

                } else {
                    mMessages.add(new Message.Builder(Message.TYPE_LEFT)
                            .username(pastMessages.get(i).user).message(pastMessages.get(i).message).build());
                    mAdapter.notifyItemInserted(mMessages.size() - 1);
                    scrollToBottom();
                }
            }
        }

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

        chatAttachButton = (ImageButton)findViewById(R.id.chatAttachButton);
        chatAttachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(ChattingDetailActivity.this, NewMessageAlertActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
             Toast.makeText(ChattingDetailActivity.this,"서비스 준비중",Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();


    }


    @Override
    protected void onResume() {
        super.onResume();
//        isAleady = true;
//        //mSocket.on("toClient",toClient);

        if (pastMessages != null && pastMessages.size() > 0) {
            pastMessages.removeAll(pastMessages);

            Log.e("resumesize", String.valueOf(pastMessages.size()));
        }

    }

    private void addMessage(int username, String message) {
        if (username == userAccount) {
            mMessages.add(new Message.Builder(Message.TYPE_RIGHT)
                    .username(username).message(message).build());
            Log.e("right", String.valueOf(username));
            mAdapter.notifyItemInserted(mMessages.size() - 1);
            insertMessage(message, username);
            scrollToBottom();
        }
    }

    private void addMessageLeft(int username, String message) {

        if (username != userAccount) {
            mMessages.add(new Message.Builder(Message.TYPE_LEFT)
                    .username(username).message(message).build());
            Log.e("left", String.valueOf(username));
            mAdapter.notifyItemInserted(mMessages.size() - 1);
            insertMessage(message, username);
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
            //perform the sending message attempt

            Log.e("toserver:", message);
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
                        Log.e("to client sender", String.valueOf(data.getString("msg")));
                        username = data.getInt("sender");
                        message = data.getString("msg");



                    } catch (JSONException e) {
                        return;
                    }


                    //  removeTyping(username);
                    //  if (username != userAccount) {
                    addMessageLeft(username, message);

                    //  }
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
                    Bundle bundle = new Bundle();
                    bundle.putInt("targetUserCode", targetUserCode);
                    Log.e("targetUserCode detail", String.valueOf(targetUserCode));
                    dialog.setArguments(bundle);
                    dialog.show(getFragmentManager(), "requestDialog");
                    break;
                case R.id.chatDetailSendButton:
                    break;

            }
        }
    };


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
        type = intent.getIntExtra("type", 0);
        if (type == BOTTOMBAR) {
            //bottom navigation으로 이동한 경우 ChattingActivity에서 넘어옴
            roomNum = intent.getIntExtra("roomNum", 0);
            targetUserImg = intent.getExtras().getString("targetUserImg"); //상대방 이미지
            targetUserName = intent.getExtras().getString("targetUserName"); //상대방 이름
            targetUserCode = intent.getIntExtra("targetUserCode", 0);


        } else if (type == MYPAGE) {

            // OtherMypageActivity에서 넘어온 데이터들
            roomNum = intent.getIntExtra("roomNum", 0);
            // sender = intent.getIntExtra("sender", 0); //본인
            targetUserCode = intent.getIntExtra("receiver", 0); //상대방
            targetUserName = intent.getStringExtra("receiverName");
            targetUserImg = intent.getStringExtra("receiverImg");

        } else if (type == PUSH) {
            roomNum = intent.getIntExtra("roomNum", 0);
            targetUserCode = intent.getIntExtra("receiver", 0); //상대방
            targetUserName = intent.getStringExtra("receiverName");
            targetUserImg = intent.getStringExtra("receiverImg");

        }

    }


    //TODO : insert message 만들기
    public void insertMessage(String message, int user) {

        dbHelper.insert(roomNum, message, user);
        Log.e("message ", user + message);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSocket.off("toClient",toClient);
//        mSocket.disconnect();
//
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off("toClinet", toClient);
//        isAleady = true;
//        //sharedPreferenceUtil.setAccessToClient(0);
        // sharedPreferenceUtil.removeAccessToClient();

    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        mSocket.disconnect();
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off("toClinet", toClient);
//        isAleady = true;
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mSocket.disconnect();
//        mSocket.off(Socket.EVENT_CONNECT, onConnect);
//        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off("toClinet", toClient);
//    }

    //    @Override
//    protected void onRestart() {
//        super.onRestart();
//        mSocket.on(Socket.EVENT_CONNECT, onConnect);
//        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.off("toClinet", toClient);
//    }
    public void joinRoom() {
        JSONObject object = new JSONObject();
        try {
            object.put("room", roomNum);
            object.put("user", userAccount); //본인 아이디
            Log.e("Chatting User sender", String.valueOf(userAccount));
            Log.e("Chatting roomnum", String.valueOf(roomNum));

            mSocket.emit("joinRoom", object);
        } catch (JSONException e) {
            Log.d("SEND MESSAGE", "ERROR");
            e.printStackTrace();
        }

    }



}
