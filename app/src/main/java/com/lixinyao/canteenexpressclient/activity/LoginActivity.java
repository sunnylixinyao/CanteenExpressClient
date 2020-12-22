package com.lixinyao.canteenexpressclient.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.lixinyao.canteenexpressclient.fragment.LogininFragment;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.fragment.RegisterFragment;
import com.lixinyao.canteenexpressclient.network.HttpUtil;
import com.lixinyao.canteenexpressclient.util.JSONtools;
import com.lixinyao.canteenexpressclient.util.VerifyCode;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.lixinyao.canteenexpressclient.util.DataResultError.M000000;


public class LoginActivity extends Activity implements View.OnClickListener {
    //返回按钮
    private Button BackButton;

    //两个Fragment页面切换按钮
    private TextView ToRegister;
    private TextView ToLoginin;
    //声明注册页面的验证码获取按钮
    private TextView verifyCodeButton;
    //声明系统发过来的验证码
    private String verifyCodeBySY;
    //声明Notification的ID
    private static final int notification_ID=1234;


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
//            case R.id.getcode:
//                //点击获取验证码按钮,系统模拟验证码获取过程
//                VerifyCode verifyCode=new VerifyCode(1);
//                sendData(verifyCode);
//                verifyCodeButton.setText("已发送");
//                if(verifyCodeBySY!=null){
//                    //说明获取到系统发送的验证码，将其通过Notification通知出来
//                }
//                break;

        }
    }
    //通过Notification显示验证码
    private void showVerifyCode(){
        NotificationCompat.Builder builder =new NotificationCompat.Builder(LoginActivity.this);
        builder.setSmallIcon(R.drawable.verifycode);
        builder.setContentTitle("注册验证码");
        builder.setContentText("您的注册验证码是： "+verifyCodeBySY);
        Notification notification=new Notification();
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notification_ID,notification);
    }
    private void sendData(VerifyCode verifyCode){
        HttpUtil.httpOkHttpRequest("clientVerifyCodeServlet",verifyCode, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ResponseData=response.body().string();
                ArrayList<String> data= JSONtools.jsonToArray(ResponseData);
//                for (int i = 0; i < data.size(); i++) {
//                    Log.i(TAG,"data"+data.get(i));
//                }
                if (data.get(2).equals(M000000.getCODE())){
                    verifyCodeBySY=data.get(6);
                }else {
                    Toast.makeText(LoginActivity.this, data.get(4), Toast.LENGTH_SHORT).show();
                    verifyCodeButton.setText("点击获取验证码");
                }
            }
        });
    }
}