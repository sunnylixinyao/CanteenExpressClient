package com.lixinyao.canteenexpressclient.activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.lixinyao.canteenexpressclient.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PersonActivity extends Activity implements View.OnClickListener {

    private String username;
    private String money;
    private SharedPreferences sharedPreferences;
    private Button ButtonBack;
    private TextView TXTusername;
    private TextView TXTusermoney;


    private String TAG="PersonActivity";

    private static final int TAKE_PHOTO=1;
    private ImageView Userimage;
    private Button BtnuploadImage;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        sharedPreferences = getSharedPreferences("use_info", Context.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ButtonBack=findViewById(R.id.Button_back);
        ButtonBack.setOnClickListener(this);
        TXTusername=findViewById(R.id.username);
        TXTusermoney=findViewById(R.id.usermoney);
        BtnuploadImage=findViewById(R.id.upload_image);
        BtnuploadImage.setOnClickListener(this);
        Userimage=findViewById(R.id.userimage);
        //设置用户的基本信息
        setText();
    }

    private void setText(){
        //获取用户登录时保存下来的信息
        username=sharedPreferences.getString("name","同学");
        money=sharedPreferences.getString("study_ID","0");
        TXTusername.setText(username);
        TXTusermoney.setText(money);
        Log.i(TAG, username);
        Log.i(TAG,money);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Button_back:
                finish();
                break;
            case R.id.upload_image:
                //点击上传图片按钮时调用此函数
                 takePhoto();
                break;
            default:
                break;
        }
    }
    private void takePhoto(){
        //创建file对象，用于存储拍照后的图片
        //getExternalCacheDir表示将图片存储到手机SD卡的应用关联缓存目录下
        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(PersonActivity.this,"com.lixinyao.canteenexpressclient.fileprovider",outputImage);
        }else {
            imageUri=Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        //调用startActivityForResult这个方法来跳转
        startActivityForResult(intent,TAKE_PHOTO);
    }

    //take photo result return
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(requestCode==RESULT_OK){
                    try{
                        //将拍摄的图片显示出来
                        //use this decodeStream() change .jpg to Bitmap
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        //将bitmap对象set到图片框中
                        Userimage.setImageBitmap(bitmap);
                        Log.i(TAG,"userimage is "+Userimage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}