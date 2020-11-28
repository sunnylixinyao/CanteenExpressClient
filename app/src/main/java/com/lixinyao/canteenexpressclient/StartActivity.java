package com.lixinyao.canteenexpressclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public class StartActivity extends Activity {

    //判断用户是否是第一次使用该应用
    private boolean isFirstUse=false;
    //延时时间，由欢迎界面进入另一个界面的延时
    private static final int TIME=2*1000;

    private static final int TO_GUIDE=100001;
    private static final int TO_HOME=100002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
    }
    //不能直接在主线程中延时，因此使用handler来处理发送过来的消息
    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(android.os.Message message) {
            switch (message.what){
                case TO_GUIDE:
                    //跳转到引导页面
                    Intent intent1=new Intent(StartActivity.this,GuideActivity.class);
                    startActivity(intent1);
                    break;
                case TO_HOME:
                    //跳转到主页面
                    Intent intent2=new Intent(StartActivity.this,HomeActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };
    private void init() {
        //将用户是都第一次使用的值用sharePreferences存储到本地
        SharedPreferences sharedPreferences=getSharedPreferences("Share",MODE_PRIVATE);
        isFirstUse=sharedPreferences.getBoolean("isFirstUse",true);
        if(!isFirstUse){
            //isFirstUse为false,说明不是第一次使用，因此跳转到主页面
            myHandler.sendEmptyMessageDelayed(TO_HOME,TIME);
        }else {
            //说明是第一次使用，跳转到引导页面
            myHandler.sendEmptyMessageDelayed(TO_GUIDE,TIME);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isFirstUse",false);
            editor.commit();
        }
    }
}