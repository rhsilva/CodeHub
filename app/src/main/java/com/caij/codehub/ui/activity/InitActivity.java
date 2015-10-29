package com.caij.codehub.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caij.codehub.Constant;
import com.caij.codehub.R;
import com.caij.codehub.presenter.BasePresent;
import com.caij.lib.utils.SPUtils;

public class InitActivity extends BaseActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        Glide.with(this).load(R.drawable.splash).centerCrop().into(imageView);
        setFullScreen();
        mHandler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (TextUtils.isEmpty(SPUtils.get(Constant.USER_TOKEN, ""))) {
                    intent = new Intent(InitActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(InitActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }
}
