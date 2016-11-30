package com.tacademy.sadajo.chatting;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;

import static com.tacademy.sadajo.R.id.listView;

public class ChattingDetailActivity extends BaseActivity {

    Toolbar toolbar;

    ListView chatlistView;

    ImageButton requestButton;
    ImageButton chatDetailSendButton;

    EditText chattingEditText;

    ChattingAdapter chatMessageArrayAdapter;
    boolean side= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_detail);

        toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//title hidden
        setToolbar(true);



        chatlistView =  (ListView)findViewById(listView);
        chatDetailSendButton = (ImageButton)findViewById(R.id.chatDetailSendButton);
        chattingEditText = (EditText)findViewById(R.id.chattingEditText);

        chatMessageArrayAdapter = new ChattingAdapter(ChattingDetailActivity.this,R.layout.chatting_message);
        chatlistView.setAdapter(chatMessageArrayAdapter);


        chattingEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();

                }
                return false;
            }
        });

        chatlistView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        chatlistView.setAdapter(chatMessageArrayAdapter);

        chatMessageArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                chatlistView.setSelection(chatMessageArrayAdapter.getCount()-1);
            }
        });

        requestButton = (ImageButton)findViewById(R.id.requestButton);//사다조 요청하기 버튼
        requestButton.setOnClickListener(clickListener);
        chatDetailSendButton.setOnClickListener(clickListener);



    }
     boolean sendChatMessage() {
         chatMessageArrayAdapter.add(new ChatMessage(side, chattingEditText.getText().toString()));
        chattingEditText.setText("");
        side = !side;
        return true;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.requestButton:
                    RequestDialogFragment dialog = new RequestDialogFragment();
                    dialog.show(getFragmentManager(), "requestDialog");
                    break;
                case R.id.chatDetailSendButton:
                    sendChatMessage();
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
