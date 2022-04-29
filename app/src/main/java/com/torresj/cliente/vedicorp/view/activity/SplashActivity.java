package com.torresj.cliente.vedicorp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.torresj.cliente.vedicorp.R;

public class SplashActivity extends AppCompatActivity {

    Animation topAnim ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageView ImgSplash = (ImageView) findViewById(R.id.ImgSplash);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        ImgSplash.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                decideNextActivity();
            }
        }, 3000);
    }

        private void decideNextActivity() {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

        @Override
        protected void onPause() {
            super.onPause();
            finish();
        }


    }