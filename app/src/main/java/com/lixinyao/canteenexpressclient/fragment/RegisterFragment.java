package com.lixinyao.canteenexpressclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.network.HttpUtil;
import com.lixinyao.canteenexpressclient.util.ClientRegister;
import com.lixinyao.canteenexpressclient.util.JSONtools;
import com.lixinyao.canteenexpressclient.util.VerifyCode;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

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
    //获取验证码按钮
    private TextView verifyCodeButton;
    //声明GSON
    private Gson gson;

    //声明系统发过来的验证码
    private String verifyCodeBySY;

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
        verifyCodeButton=view.findViewById(R.id.getcode);
        ButtonRegister.setOnClickListener(this);
        verifyCodeButton.setOnClickListener(this);
        //初始化GSON对象
        gson=new Gson();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Register_Button:
                //当点击Button之后，将信息传递到后端
                //目前不做到校园网的验证
                name=Editname.getText().toString();
                study_ID=Editstudy_ID.getText().toString();
                tel=Editpassword.getText().toString();
                password=Editpassword.getText().toString();
                verfCode=EditverfCode.getText().toString();
                ClientRegister clientRegister=new ClientRegister(name,study_ID,tel,password);
                //发送网络请求
                Log.i(TAG,"client register data"+gson.toJson(clientRegister));
                sendData(clientRegister);
                break;
            case R.id.getcode:
            //点击获取验证码按钮,系统模拟验证码获取过程
                VerifyCode verifyCode=new VerifyCode(1);
                sendData(verifyCode);
                verifyCodeButton.setText("已发送");
                break;
        }
    }
    private void sendData(ClientRegister clientRegister){
        HttpUtil.httpOkHttpRequest("clientRegister",clientRegister, new okhttp3.Callback() {

            @Override
            public void onResponse(Call call,Response response) throws IOException {
            }
            @Override
            public void onFailure(Call call,IOException e) {
            }
        });
    }
    private void sendData(VerifyCode verifyCode){
        HttpUtil.httpOkHttpRequest("clientVerifyCodeServlet",verifyCode, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call,IOException e) {

            }
            @Override
            public void onResponse(Call call,Response response) throws IOException {
                String ResponseData=response.body().string();
                Log.i(TAG,"response data is "+ResponseData);
            }
        });
    }
}
