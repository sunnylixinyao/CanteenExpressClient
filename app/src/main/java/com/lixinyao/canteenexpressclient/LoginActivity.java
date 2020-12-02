package com.lixinyao.canteenexpressclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends Activity implements View.OnClickListener {
    //返回按钮
    private Button BackButton;
    //登录按钮
    private Button LogininButton;
    //注册按钮
    private Button RegisterButton;

    //两个Fragment页面切换按钮
    private TextView ToRegister;
    private TextView ToLoginin;

    //声明FragmentManaer和FragmentTraction
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    //声明两个碎片
    private LogininFragment logininFragment;
    private RegisterFragment registerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inite();
    }

    @Override
    protected void onStart() {
        super.onStart();
        findView();
    }
    //获取控件
    private void findView(){
        //通过Fragment实例获取控件
       BackButton = logininFragment.getView().findViewById(R.id.back);
       LogininButton = logininFragment.getView().findViewById(R.id.Loginin_button);
       ToRegister=logininFragment.getView().findViewById(R.id.ToRegister);
       //RegisterButton = registerFragment.getView().findViewById(R.id.Register_Button);
       //ToLoginin=registerFragment.getView().findViewById(R.id.ToLogin);
       //设置控件的点击事件
       BackButton.setOnClickListener(this);
       LogininButton.setOnClickListener(this);
       //RegisterButton.setOnClickListener(this);
    }
    //初始化页面布局
    //登录注册功能刚开始是登录页面

    private void inite() {
        fragmentManager = getFragmentManager();
        logininFragment = new LogininFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        registerFragment = new RegisterFragment();
        fragmentTransaction.add(R.id.content_fragment,logininFragment);
        fragmentTransaction.commit();
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                //点击返回按钮时，跳转到主页面
                finish();
                break;
            case R.id.Loginin_button:
                //点击登录按钮，进行账号的判断
                judge();
                break;
            case R.id.Register_Button:
                //点击注册按钮，将数据保存到数据库中
                register();
                break;
            case R.id.ToRegister:
                //加载注册页面Fragment
              // getFragmentManager().beginTransaction().replace(R.id.content_fragment,registerFragment);
                break;
            case R.id.ToLogin:
                //加载登录页面
                break;
        }
    }
    //获取到LogininFragment发送过来的用户的学号/电话号码和密码
    //传递到后端进行校验
    private void judge(){

    }
    //将数据保存到数据库中
    private void register(){

    }
}