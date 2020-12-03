package com.lixinyao.canteenexpressclient.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixinyao.canteenexpressclient.fragment.LogininFragment;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.fragment.RegisterFragment;


public class LoginActivity extends Activity implements View.OnClickListener {
    //返回按钮
    private Button BackButton;

    //两个Fragment页面切换按钮
    private TextView ToRegister;
    private TextView ToLoginin;

    //声明FragmentManaer和FragmentTraction
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    //声明两个碎片
    private LogininFragment logininFragment;
    private RegisterFragment registerFragment;

    //声明一个Boolean变量来判断加载的是LoginFragment还是RegisterFragment
    private boolean isLoginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inite();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inintView();
    }
    private void inintView(){
        ToRegister=findViewById(R.id.ToRegister);
        ToLoginin=findViewById(R.id.ToLogin);
        ToRegister.setOnClickListener(this);
        ToLoginin.setOnClickListener(this);
    }
    //初始化页面布局
    //登录注册功能刚开始是登录页面
    private void inite() {
        BackButton=findViewById(R.id.back);
        BackButton.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        logininFragment = new LogininFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        registerFragment = new RegisterFragment();
        //讲LogininFragment加载到布局中，并添加到返回栈中
        fragmentTransaction.add(R.id.content_fragment,logininFragment);
        isLoginFragment=true;
        bootomInit();
        //添加到返回栈中
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void bootomInit(){
        LinearLayout LoginlinearLayout=findViewById(R.id.LoginPage);
        LinearLayout RegisterlinearLayout=findViewById(R.id.RegisterPage);
        if (isLoginFragment){
            LoginlinearLayout.setVisibility(View.VISIBLE);
            RegisterlinearLayout.setVisibility(View.GONE);
        }else{
            LoginlinearLayout.setVisibility(View.GONE);
            RegisterlinearLayout.setVisibility(View.VISIBLE);
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                //点击返回按钮时，跳转到主页面
                finish();
                break;
            case R.id.ToRegister:
                //点击跳转到注册页面
                isLoginFragment=false;
                bootomInit();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_fragment,registerFragment);
                fragmentTransaction.commit();
                break;
            case R.id.ToLogin:
                //点击加载登录页面
                isLoginFragment=true;
                bootomInit();
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_fragment,logininFragment);
                fragmentTransaction.commit();
                break;
        }
    }
}