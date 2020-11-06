package com.lixinyao.canteenexpressclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //利用Handler做页面的延时跳转
        //由欢迎页面跳转到主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this,HomeActivity.class));
                finish();
            }
        },1000);
    }
}