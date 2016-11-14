package com.tacademy.sadajo.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tacademy.sadajo.HomeActivity;
import com.tacademy.sadajo.R;

public class SplashActivity extends Activity {
    private AnimatedGifImageView animatedGifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_main);
        animatedGifImageView = ((AnimatedGifImageView)findViewById(R.id.animatedGifImageView));
        animatedGifImageView.setAnimatedGif(R.raw.splash,
                AnimatedGifImageView.TYPE.FIT_CENTER);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
               finish();       }
        }, 1600);

   }
    Handler handler = new Handler();
}
