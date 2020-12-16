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
import com.lixinyao.canteenexpressclient.util.Client;
import com.lixinyao.canteenexpressclient.util.JSONtools;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.lixinyao.canteenexpressclient.util.DataResultError.M000000;
import static com.lixinyao.canteenexpressclient.util.DataResultError.M000001;


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

    //用来保存用户点击登录按钮之后的登录状态
    //根据后端传来的RECODE来判断
    private int loginstate;
    //1:成功登录
    //2:登录失败，没有该用户
    //-1：系统错误
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
                Log.i(TAG, "study_ID is "+ID);
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
                //Log.i(TAG,response.body().string());
                //获得后端传递过来的内容
                String ResponseData=response.body().string();
                Log.i(TAG,ResponseData);
                //将后端传来的数据保存为数组
                //更好取出
                ArrayList<String> data = JSONtools.jsonToArray(ResponseData);
//                for (int i = 0; i < data.size(); i++) {
//                   Log.i(TAG,data.get(i));
//                }
                if(data.get(2).equals(M000000.getCODE())){
                    //如返回的code为000000，则说明已经查询到，并且争取返回
                    loginstate=1;
                }else if(data.get(2).equals(M000001.getCODE())){
                    loginstate=2;
                }else {
                    loginstate=-1;
                }
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
