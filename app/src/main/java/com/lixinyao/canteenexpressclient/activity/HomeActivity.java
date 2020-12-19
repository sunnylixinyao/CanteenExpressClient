package com.lixinyao.canteenexpressclient.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.githang.statusbar.StatusBarCompat;
import com.lixinyao.canteenexpressclient.fragment.MenuFragment;
import com.lixinyao.canteenexpressclient.fragment.NoPersinInfoFragment;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.network.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class HomeActivity extends Activity implements View.OnClickListener {

    private String name;
    private Button TurnToPerson;
    private String TAG="HomeActivity";
    //用来保存用户登录状态
    private int loginstate;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i(TAG,"homepage");
        //设置状态栏的颜色与系统的颜色相同，需要引入依赖status-bar-compat
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.colorMain), false);
       //初始化控件
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginstate= sharedPreferences.getInt("LoginState", -1);
        System.out.println("onRusume " + loginstate);
        judgeLogin();
    }

    private void initView() {
        //初始化页面布局时就应该调用
        sharedPreferences = getSharedPreferences("use_info", Context.MODE_PRIVATE);
        loginstate= sharedPreferences.getInt("LoginState", -1);
        System.out.println("sharePreference loginstate is " + loginstate);
        TurnToPerson=(Button)findViewById(R.id.home_person);
        judgeLogin();
        TurnToPerson.setOnClickListener(this);
    }
    private void judgeLogin(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Log.i(TAG,"judge state"+loginstate);
        if(loginstate==1){
            MenuFragment menuFragment=new MenuFragment();
            fragmentTransaction.replace(R.id.content_fragment,menuFragment);
            fragmentTransaction.commit();
        }else{
            NoPersinInfoFragment noPersinInfoFragment=new NoPersinInfoFragment();
            fragmentTransaction.replace(R.id.content_fragment,noPersinInfoFragment);
            fragmentTransaction.commit();
        }
    }
    public void onClick(View v){
        switch(v.getId()){
            //当点击个人中心按钮的时候跳转到个人中心页面
            case R.id.home_person:
                Log.i(TAG,"onclick state"+loginstate);
                if (loginstate==1){
                    startActivity(new Intent(HomeActivity.this,PersonActivity.class));
                }else {
                    startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                }
        }
    }
}