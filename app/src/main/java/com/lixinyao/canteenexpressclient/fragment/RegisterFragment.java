package com.lixinyao.canteenexpressclient.fragment;

import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.activity.HomeActivity;
import com.lixinyao.canteenexpressclient.activity.LoginActivity;
import com.lixinyao.canteenexpressclient.network.HttpUtil;
import com.lixinyao.canteenexpressclient.util.ClientRegister;
import com.lixinyao.canteenexpressclient.util.JSONtools;
import com.lixinyao.canteenexpressclient.util.VerifyCode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.lixinyao.canteenexpressclient.util.DataResultError.M000000;
import static com.lixinyao.canteenexpressclient.util.DataResultError.M000004;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    //获取前端传来的数据
    private String name;
    private String study_ID;
    private String tel;
    private String password;
    private String verfCode;

    //声明EditText
    private EditText Editname;
    private EditText Editstudy_ID;
    private EditText Edittel;
    private EditText Editpassword;
    private EditText EditverfCode;

    //注册按钮
    private Button ButtonRegister;
    //声明GSON
    private Gson gson;

    //声明一个sharePreference对象
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    //声明一个变量用来保存用户的注册状态
    private int RegisterState;
    //0:表示成功
    //1：表示该账号已经注册
    //-1：表示系统错误

    //TAG
    private String TAG="RegisterFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragement_register,container,false);
        init(view);
        return view;
    }
    private void init(View view){
        Editname=view.findViewById(R.id.name);
        Editstudy_ID=view.findViewById(R.id.ID);
        Edittel=view.findViewById(R.id.Tel);
        Editpassword=view.findViewById(R.id.password);
        EditverfCode=view.findViewById(R.id.code);
        ButtonRegister=view.findViewById(R.id.Register_Button);
        ButtonRegister.setOnClickListener(this);
        //初始化GSON对象
        gson=new Gson();

        //初始化对象sharePreferences
        sharedPreferences=getActivity().getSharedPreferences("use_info",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Register_Button:
                //当点击Button之后，将信息传递到后端
                //目前不做到校园网的验证
                name=Editname.getText().toString();
                study_ID=Editstudy_ID.getText().toString();
                tel=Edittel.getText().toString();
                password=Editpassword.getText().toString();
                verfCode=EditverfCode.getText().toString();
                ClientRegister clientRegister=new ClientRegister(name,study_ID,tel,password);
                //发送网络请求
                Log.i(TAG,"client register data"+gson.toJson(clientRegister));
                sendData(clientRegister);
                break;
        }
    }

    private void sendData(ClientRegister clientRegister){
        HttpUtil.httpOkHttpRequest("clientRegister",clientRegister, new okhttp3.Callback() {

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                String ResponseData=response.body().string();
                ArrayList<String> data= JSONtools.jsonToArray(ResponseData);
//                for (int i = 0; i < data.size(); i++) {
//                    Log.i(TAG,"data"+data.get(i));
//                }
                //获取系统当前时间
                Date date=new Date(System.currentTimeMillis());
                String day=data.toString();
                Time RegisterTime=new Time(System.currentTimeMillis());
                String time=RegisterTime.toString();
                //判断使用注册成功
                //若成功则跳转
                if(data.get(2).equals(M000000.getCODE())){
                    //将数据存储
                    editor.putString("name",name);
                    editor.putInt("LoginState",1);
                    editor.putString("study_ID",study_ID);
                    editor.putString("day",day);
                    editor.putString("time",time);
                    editor.apply();
                    RegisterState=0;
                    //跳转到homePage
                    Intent intent=new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }else if(data.get(2).equals(M000004.getCODE())){
                    RegisterState=1;
//                    Log.i(TAG,"执行到这里");
                    Looper.prepare();
                    Toast.makeText(getActivity(), getResources().getString(R.string.accountexisted), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else {
                    RegisterState=-1;
                    Looper.prepare();
                    Toast.makeText(getActivity(), getResources().getString(R.string.systemwrong), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
            @Override
            public void onFailure(Call call,IOException e) {
            }
        });
    }
}
