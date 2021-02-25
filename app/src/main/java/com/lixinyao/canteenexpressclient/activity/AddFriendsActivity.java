package com.lixinyao.canteenexpressclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.network.HttpUtil;
import com.lixinyao.canteenexpressclient.util.ClientByName;
import com.lixinyao.canteenexpressclient.util.DataResultError;
import com.lixinyao.canteenexpressclient.util.JSONtools;
import com.lixinyao.canteenexpressclient.util.UserAddFriend;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class AddFriendsActivity extends Activity implements View.OnClickListener {


    private ImageView Back;

    private EditText EditName;
    private Button BtnSearch;
    private TextView TxtName;
    private Button BtnAdd;


    //获取到用户输入的姓名
    private String name;

    private String result="";


    //获取到该用户的姓名
    private String userName="";
    //获取到输入姓名用户的信息
    private String friendName="";
    private String friendStudy_id="";


    private String TAG="AddFriendsActivity";

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.colorMain), false);
        sharedPreferences = getSharedPreferences("use_info", Context.MODE_PRIVATE);
        userName=sharedPreferences.getString("name","同学");
        Back=findViewById(R.id.back);
        Back.setOnClickListener(this);
        EditName=findViewById(R.id.EditName);
        EditName.setOnClickListener(this);
        BtnSearch=findViewById(R.id.BtnSearch);
        BtnSearch.setOnClickListener(this);
        TxtName=findViewById(R.id.TxtName);
        BtnAdd=findViewById(R.id.BtnAdd);
        BtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case R.id.back:
               finish();
               break;
           case R.id.BtnSearch:
               name=EditName.getText().toString();
               ClientByName clientByName=new ClientByName(name);
               sendClientInfo(clientByName);
               Log.i(TAG,"执行到这里了,并且result is"+result);
               break;
            default:
                break;
       }
    }

    public void sendClientInfo(final ClientByName clientByName){
        HttpUtil.httpOkHttpRequest("getClientInfo",clientByName, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call,IOException e) {

            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                Log.i(TAG, "网络成功了");
                String ResponseData=response.body().string();
                Log.i(TAG,ResponseData);
                //将后端传来的数据保存为数组
                //更好取出
                ArrayList<String> data = JSONtools.jsonToArray(ResponseData);
                for (int i = 0; i < data.size(); i++) {
                    Log.i(TAG,data.get(i));
                }
                if(data.get(2).equals(DataResultError.M000009.getCODE())){
                   result="该用户不存在";
                }
                if (data.get(2).equals(DataResultError.M000000.getCODE())){
                    Log.i(TAG,"此时已经被执行了");
                    StringBuilder clientInfo=new StringBuilder();
                    clientInfo.append("姓名是");
                    clientInfo.append(data.get(9));
                    friendName=data.get(9);
                    clientInfo.append("  学号是");
                    clientInfo.append(data.get(11));
                    friendStudy_id=data.get(11);
                    Log.i(TAG,"结果是"+clientInfo.toString());
                    result=clientInfo.toString();
                }
                if (data.get(2).equals(DataResultError.M999999.getCODE())){
                    result="系统小差了~稍后再试";
                }
                Handler mainHandler=new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        TxtName.setText(result);
                        BtnAdd.setVisibility(View.VISIBLE);
                        UserAddFriend userAddFriend=new UserAddFriend(userName,friendName,friendStudy_id);
                        sendAddFriendData(userAddFriend);
                    }
                });
            }
        });
    }
    public void sendAddFriendData(UserAddFriend userAddFriend){
        HttpUtil.httpOkHttpRequest("addFriends",userAddFriend, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call,IOException e) {
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                Log.i(TAG, "网络成功了");
                String ResponseData=response.body().string();
                Log.i(TAG,ResponseData);
                //将后端传来的数据保存为数组
                //更好取出
                ArrayList<String> data = JSONtools.jsonToArray(ResponseData);
                for (int i = 0; i < data.size(); i++) {
                    Log.i(TAG,data.get(i));
                }
            }
        });
    }
}