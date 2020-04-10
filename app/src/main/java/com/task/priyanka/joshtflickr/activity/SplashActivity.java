package com.task.priyanka.joshtflickr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.task.priyanka.joshtflickr.R;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifDrawable;

public class SplashActivity extends AppCompatActivity {
    ImageView iv_mic;
    private static final int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUi();
        splashHandler();
    }

    private void initUi() {
        iv_mic = findViewById(R.id.iv_mic);
        try {
            GifDrawable gifFromResource = new GifDrawable( getResources(), R.drawable.micc);
            iv_mic.setImageDrawable(gifFromResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void splashHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
