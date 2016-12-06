package com.tacademy.sadajo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tacademy.sadajo.home.HomeActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    ImageButton kakaoLoginImageButton;
    ImageButton faceBookLoginImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1600);

        faceBookLoginImageButton = (ImageButton) findViewById(R.id.faceBookLoginImageButton);
        kakaoLoginImageButton = (ImageButton) findViewById(R.id.kakaoLoginImageButton);
        faceBookLoginImageButton.setOnClickListener(this);
        kakaoLoginImageButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.faceBookLoginImageButton:
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.kakaoLoginImageButton:
                intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
// Handler handler = new Handler();
}
