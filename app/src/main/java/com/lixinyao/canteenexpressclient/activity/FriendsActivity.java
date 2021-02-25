package com.lixinyao.canteenexpressclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.githang.statusbar.StatusBarCompat;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.network.HttpUtil;
import com.lixinyao.canteenexpressclient.util.ClientByName;
import com.lixinyao.canteenexpressclient.util.JSONtools;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class FriendsActivity extends Activity implements View.OnClickListener {

    private ImageView BackButton;
    private ImageView TurntoTel;
    private ImageView AddFriends;

    //好友列表
    private ListView FriendslistView;


    private ArrayAdapter<String> adapter;
    private String TAG="FriendsActivity";
    //获取目前用户的名字
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.colorMain), false);
        BackButton=findViewById(R.id.back);
        BackButton.setOnClickListener(this);
        TurntoTel=findViewById(R.id.turntoTelphone);
        TurntoTel.setOnClickListener(this);
        AddFriends=findViewById(R.id.addFriends);
        AddFriends.setOnClickListener(this);
        FriendslistView=findViewById(R.id.friendsList);
        sharedPreferences = getSharedPreferences("use_info", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("name","同学");
        ClientByName clientByName=new ClientByName(username);
        sendFriensData(clientByName);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void sendFriensData(ClientByName clientByName){
       HttpUtil.httpOkHttpRequest("getFriendsData",clientByName, new okhttp3.Callback() {
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
               ArrayList<String>  Frirnds=new ArrayList<>();
               for (int i=9;i<data.size();i=i+10){
                   Frirnds.add(data.get(i));
               }
               final String[] AdapterData=new String[Frirnds.size()];
               //将数据转换为数组进行存储
               for (int i=0;i<Frirnds.size();i++){
                   AdapterData[i]=Frirnds.get(i);
                   Log.i(TAG,"Province is "+AdapterData[i]);
               }
               adapter=new ArrayAdapter<String>(FriendsActivity.this,android.R.layout.simple_list_item_1,AdapterData);
               FriendslistView.setAdapter(adapter);
           }
       });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.turntoTelphone:
                //跳转到手机联系人界面
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
            case R.id.addFriends:
                //跳转到添加好友界面
                startActivity(new Intent(FriendsActivity.this,AddFriendsActivity.class));
                break;
            default:
                break;
        }
    }
}