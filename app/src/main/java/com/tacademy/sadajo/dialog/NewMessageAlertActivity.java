package com.tacademy.sadajo.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.tacademy.sadajo.R;

public class NewMessageAlertActivity extends Activity {


    ImageButton messageCheckButton;
    ImageButton messageCancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_message_dialog);



    }



}