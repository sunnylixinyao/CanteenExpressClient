package com.lixinyao.canteenexpressclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.lixinyao.canteenexpressclient.R;
import com.lixinyao.canteenexpressclient.network.HttpUtil;
import com.lixinyao.canteenexpressclient.util.City;
import com.lixinyao.canteenexpressclient.util.DataResultError;
import com.lixinyao.canteenexpressclient.util.JSONtools;
import com.lixinyao.canteenexpressclient.util.Province;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class LocationActivity extends Activity {

    //用来显示用户当前的位置
    private TextView postion_text;

    //主线程中声明LocationClient类对象,该对象初始化需传入Context类型参数,使用getApplicationConext()方法获取全进程有效的Context。
    public LocationClient mLocationClient=null;
    private MyLocationListener myListener = new MyLocationListener();
    private String TAG="LocationActivity";
    private String ResultTAG="ResultTAG";
    private String CityTag="CityTag";
    private String SchoolTag="SchoolTag";
    private boolean getProvinceData=false;

    private ListView ProvincelistView;
    private ListView CityListView;
    private ListView SchoolListView;

    private ArrayAdapter<String> Cityadapter;
    private ArrayAdapter<String> Schooladapter;
    //声明GSON对象
    private Gson gson;

    private int positionIndex;
    private String positionName;

    private TextView Notification;

    private int Flag=0;//为0时表示此时listView中显示的是省份
    //为1时表示此时listView中显示的是城市
    //为2时表示此时listView中显示的是学校

    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        setContentView(R.layout.activity_location);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.colorMain), false);
        postion_text=findViewById(R.id.position_text);
        ProvincelistView=findViewById(R.id.Province_list);
        CityListView=findViewById(R.id.City_list);
        SchoolListView=findViewById(R.id.School_list);
        Notification=findViewById(R.id.notification);
        requestLocation();
        //生成GSON对象
        gson=new Gson();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendData(new Object());
    }

    private void sendData(Object object) {
        HttpUtil.httpOkHttpRequest("getProvice",object, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call,IOException e) {

            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                Log.i(ResultTAG, "网络成功了");
                String ResponseData=response.body().string();
                Log.i(ResultTAG,ResponseData);
                //将后端传来的数据保存为数组
                //更好取出
                ArrayList<String> data = JSONtools.jsonToArray(ResponseData);
                for (int i = 0; i < data.size(); i++) {
                    Log.i(ResultTAG,data.get(i));
                }
                 ArrayList<String> Province=new ArrayList<>();
                for (int i=9;i<data.size();i=i+4){
                    Province.add(data.get(i));
                }
                final String[] AdapterData=new String[Province.size()];
                //将数据转换为数组进行存储
                for (int i=0;i<Province.size();i++){
                    AdapterData[i]=Province.get(i);
                    Log.i(ResultTAG,"Province is "+AdapterData[i]);
                }
                adapter=new ArrayAdapter<String>(LocationActivity.this,android.R.layout.simple_list_item_1,AdapterData);
                ProvincelistView.setAdapter(adapter);
                ProvincelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Flag++;
                       positionIndex=position;
                       positionName=AdapterData[position];
                       Toast.makeText(LocationActivity.this,positionName,Toast.LENGTH_SHORT).show();
                       if(Flag==2){
                           String name=positionName;
                           //说明需要获得城市的列表
                           com.lixinyao.canteenexpressclient.util.Province province=new Province(positionIndex+1,positionName);
                           senProvinceData(province);
                       }
                    }
                });
            }
        });
    }

    private void  senProvinceData(Province province){
       HttpUtil.httpOkHttpRequest("getCity",province, new okhttp3.Callback() {
           @Override
           public void onFailure(Call call,IOException e) {

           }

           @Override
           public void onResponse(Call call,Response response) throws IOException {
               Log.i(CityTag, "网络成功了");
               String ResponseData=response.body().string();
               Log.i(CityTag,ResponseData);
               //将后端传来的数据保存为数组
               //更好取出
               ArrayList<String> data = JSONtools.jsonToArray(ResponseData);
               for (int i = 0; i < data.size(); i++) {
                   Log.i(CityTag,data.get(i));
               }
               ArrayList<String> City=new ArrayList<>();
               for (int i=13;i<data.size();i=i+8){
                  City.add(data.get(i));
               }
               final String[] CityAdapterData=new String[City.size()];
               //将数据转换为数组进行存储
               for (int i=0;i<City.size();i++){
                   CityAdapterData[i]=City.get(i);
                   Log.i(CityTag,"City is "+CityAdapterData[i]);
               }
               Handler mainHandler=new Handler(getMainLooper());
               mainHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       Cityadapter=new ArrayAdapter<String>(LocationActivity.this,android.R.layout.simple_list_item_1,CityAdapterData);
                       CityListView.setAdapter(Cityadapter);
                       ProvincelistView.setVisibility(View.GONE);
                       CityListView.setVisibility(View.VISIBLE);
                       Log.i(CityTag,"执行到这里了");
                       CityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               Flag++;
                               positionIndex=position;
                                String CityName=CityAdapterData[position];
                               Toast.makeText(LocationActivity.this,CityName,Toast.LENGTH_SHORT).show();
                               if(Flag==3){
                                   String name=CityName;
                                   //说明需要获得学校的列表
                                   City city=new City(name);
                                   sendCityData(city);
                               }
                           }
                       });
               }
               });

           }
       });
    }
    public void sendCityData(City city){
        HttpUtil.httpOkHttpRequest("getSchool",city, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call,IOException e) {

            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                Log.i(SchoolTag, "网络成功了");
                String ResponseData=response.body().string();
                Log.i(SchoolTag,ResponseData);
                //将后端传来的数据保存为数组
                //更好取出
                final ArrayList<String> data = JSONtools.jsonToArray(ResponseData);
                for (int i = 0; i < data.size(); i++) {
                    Log.i(SchoolTag,data.get(i));
                }
                ArrayList<String> City=new ArrayList<>();
                for (int i=11;i<data.size();i=i+8){
                    City.add(data.get(i)+data.get(i+2));
                }
                final String[] SchoolAdapterData=new String[City.size()];
                //将数据转换为数组进行存储
                for (int i=0;i<City.size();i++){
                    SchoolAdapterData[i]=City.get(i);
                    Log.i(SchoolTag,"School is "+SchoolAdapterData[i]);
                }
                Handler mainHanlder=new Handler(getMainLooper());
                mainHanlder.post(new Runnable() {
                    @Override
                    public void run() {
                        if (data.get(2).equals(DataResultError.M000007.getCODE()));
                        {
                            Notification.setText("该城市没有学校注册系统");
                            Notification.setVisibility(View.VISIBLE);
                        }
                        if(data.get(2).equals(DataResultError.M000000.getCODE())){
                            Schooladapter=new ArrayAdapter<>(LocationActivity.this,android.R.layout.simple_list_item_1,SchoolAdapterData);
                            SchoolListView.setAdapter(Schooladapter);
                            CityListView.setVisibility(View.GONE);
                            SchoolListView.setVisibility(View.VISIBLE);
                            SchoolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String SchoolAddress=SchoolAdapterData[position];
                                    postion_text.setText(SchoolAddress);
                                }
                            });
                        }
                        if(data.get(2).equals(DataResultError.M999999.getCODE())){
                            Notification.setText("系统出小差啦~稍后重试");
                            Notification.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }
    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setIsNeedLocationPoiList(true);
       // option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(final BDLocation location) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition=new StringBuilder();
                    currentPosition.append("纬度").append(location.getLatitude()).append("\n");
                    currentPosition.append("经度").append(location.getLongitude()).append("\n");
                  //  postion_text.setText(currentPosition);
                    List<Poi> poiList=location.getPoiList();
                    boolean Flag=false;
                    String schoolAddress="";
                    for (int i=0;i<poiList.size();i++){
                        Log.i(TAG," the number is "+i);
                        Poi temp=poiList.get(i);
                        Log.i(TAG,"the name is "+temp.getName());

                        //当结果返回为1说明这个地址就是关于大学的
                        //当结果返回不为1说明没有这个地址不是关于大学的
                        if( judge(temp.getName())==1){
                            //找到学校，返回
                            Flag=true;
                            schoolAddress=temp.getName();
                            Log.i(TAG,"real address is "+schoolAddress);
                    }else{
                            postion_text.setText("系统未获取到校区，请自主选择");
                        }
                        //获得最终的位置
                        postion_text.setText(schoolAddress);
                    }
                }
            });
        }
        //将位置筛选出来
        private int judge(String address){
            char[] temp=address.toCharArray();
            int index=0;
            for (int i=0;i<temp.length;i++){
                Log.i(TAG,"address is "+temp[i]);
                if (temp[i]=='大'){
                    index=i;
                    break;
                }
            }
            if(temp[index+1]=='学'){
                return 1;
            }
            return 0;
        }
    }
}