package com.lixinyao.canteenexpressclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.network.HttpUtil;
import com.lixinyao.canteenexpressclient.service.Client;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public class LogininFragment extends Fragment implements View.OnClickListener {
    //声明学号/手机号和密码
    private String ID="";
    private String Tel="";
    private String password="";
    private String TAG="Test";
    //声明EditText
    private EditText IDorTel;
    private EditText Password;
    //声明登录按钮
    private Button Loginin;
    //声明GSON对象
    private Gson gson;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loginin, container, false);
        init(view);
        return view;
    }
    //初始化控件
    private void init(View view) {
        IDorTel=view.findViewById(R.id.IDorTel);
        Password=view.findViewById(R.id.password);
        Loginin=view.findViewById(R.id.Loginin_button);
        Loginin.setOnClickListener(this);
        //生成GSON对象
        gson=new Gson();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Loginin_button:
                //点击登录按钮
                //获取到输入的内容
                ID=IDorTel.getText().toString();
                password=Password.getText().toString();
                Log.i(TAG, "ID is "+ID);
                Log.i(TAG,"password is"+password);
                Client client=new Client(ID,password);
                Log.i(TAG, "json"+gson.toJson(client));
                sendData(client);
        }
    }
    private void sendData(Client client){
        //发送给服务端
        HttpUtil.httpOkHttpRequest("clientLogin",client,new okhttp3.Callback(){

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //处理成功吧
                Log.i(TAG, "网络成功了");
            }
            @Override
            public void onFailure(Call call,IOException e) {
                //处理失败
                Log.i(TAG, "失败了");
                Log.i(TAG, "onFailure: "+e.toString());
            }
        });
    }
}
