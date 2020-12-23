package com.lixinyao.canteenexpressclient.network;

import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

//okhttp建立http连接
public class HttpUtil {
    private static String TAG="HttpUtil";
    private static String Url="http://192.168.1.86:8080/CanteenExpressService_war_exploded/";
    private static MediaType JSON;
    public static void httpOkHttpRequest(String servlet,Object data, okhttp3.Callback callback){
        Gson gson=new Gson();
       // Log.i(TAG,"okhttp  is working");
        //接收传来的servlet地址加前面的生成需要访问的地址
        String address=Url+servlet;
        //生成一个clinet对象
        OkHttpClient client=new OkHttpClient();
        //生成一个requestBody对象
        RequestBody requestBody = RequestBody.create(JSON,gson.toJson(data));
        Request request=new Request.Builder().url(address).post(requestBody).build();
        //生成一个新的线程，并且入队
        client.newCall(request).enqueue(callback);
    }
}
