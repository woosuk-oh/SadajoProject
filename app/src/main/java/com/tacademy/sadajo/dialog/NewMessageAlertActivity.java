package com.tacademy.sadajo.dialog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tacademy.sadajo.BaseActivity;
import com.tacademy.sadajo.R;

public class NewMessageAlertActivity extends BaseActivity {


    ImageButton messageCheckButton;
    ImageButton messageCancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fragment_new_message_dialog, null);
        //setContentView(R.layout.fragment_new_message_dialog);
        messageCheckButton = (ImageButton) findViewById(R.id.messageCheckButton);
        messageCancelButton = (ImageButton) findViewById(R.id.messageCancelButton);
        AlertDialog.Builder builder = new AlertDialog.Builder(NewMessageAlertActivity.this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();


        messageCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewMessageAlertActivity.this,"push",Toast.LENGTH_LONG).show();
            }
        });
        messageCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });


    }

}