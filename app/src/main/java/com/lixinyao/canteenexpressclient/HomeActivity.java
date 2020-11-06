package com.lixinyao.canteenexpressclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.githang.statusbar.StatusBarCompat;

public class HomeActivity extends Activity implements View.OnClickListener {

    private Button TurnToPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //设置状态栏的颜色与系统的颜色相同，需要引入依赖status-bar-compat
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.colorMain), false);
       //初始化控件
        initView();
    }

    private void initView() {
        TurnToPerson=(Button)findViewById(R.id.home_person_button);
        TurnToPerson.setOnClickListener(this);
    }
    public void onClick(View v){
        switch(v.getId()){
            //当点击个人中心按钮的时候跳转到个人中心页面
            case R.id.home_person_button:
                startActivity(new Intent(HomeActivity.this,PersonActivity.class));
                finish();
        }
    }
}