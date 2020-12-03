package com.lixinyao.canteenexpressclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lixinyao.canteenexpressclient.R;

import java.util.ArrayList;

//引导页面
public class GuideActivity extends Activity implements View.OnClickListener {

    private String Tag="test";
    //创建页面上所以空间的对象
    private ImageView page_title;
    private ImageView page_icon;
    private ImageView page_leader;
    private Button start_button;
    //滑动文字图片的int数组
    private int[] contentID={R.mipmap.guide_page1_content,
    R.mipmap.guide_page2_content,
    R.mipmap.guide_page3_content};

    //用来填充ViewPager的ImageView集合
    private ArrayList<ImageView> contentView=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化数据
        initData();
    }

    private void initData() {
        //实例化控件对象
        page_title=findViewById(R.id.page_title);
        page_icon=findViewById(R.id.page_icon);
        page_leader=findViewById(R.id.page_leader);
        start_button=findViewById(R.id.start_button);
        start_button.setVisibility(View.INVISIBLE);
        start_button.setOnClickListener(this);
        //根据Id生成一个ViewPager对象
        ViewPager mViewPager=findViewById(R.id.mViewPager);
        //viewPager滑动监听
        mViewPager.addOnPageChangeListener(new MyOnPagerChangeListener());
        //通过一个循环拿出id，生成一个ImageView加入给contentView中
        for (int i=0;i<contentID.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(contentID[i]);
            contentView.add(imageView);
        }
        //设置ViewPager的适配器
        //实例化mViewPager
        mViewPager.setAdapter(new MyAdapter());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //当点击个人中心按钮的时候跳转到个人中心页面
            case R.id.start_button:
                startActivity(new Intent(GuideActivity.this,HomeActivity.class));
                finish();
        }
    }

    //监听ViewPager的滑动距离
    class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener{


        //当页面在滑动的时候会调用这个方法，在滑动被停止之前
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.v(Tag,"1"+position);
            switch (position){
                case 0:
                    page_title.setImageResource(R.mipmap.guide_page1_title);
                    page_icon.setImageResource(R.mipmap.guide_page1_icon);
                    page_leader.setImageResource(R.mipmap.guide_leader1);
                    page_leader.setVisibility(View.VISIBLE);
                    start_button.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    page_title.setImageResource(R.mipmap.guide_page2_title);
                    page_icon.setImageResource(R.mipmap.guide_page2_icon);
                    page_leader.setImageResource(R.mipmap.guide_leader2);
                    page_leader.setVisibility(View.VISIBLE);
                    start_button.setVisibility(View.INVISIBLE);
                    break;
                default:
                    page_title.setImageResource(R.mipmap.guide_page3_title);
                    page_icon.setImageResource(R.mipmap.guide_page3_icon);
                    page_leader.setImageResource(R.mipmap.guide_leader2);
                    page_leader.setVisibility(View.GONE);
                    start_button.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onPageSelected(int position) {

        }
        //此方法是在状态改变的时候调用
        //state有三种状态
        //SCROLL_STATE_IDLE（0）滑动动画做完的状态。
        //SCROLL_STATE_DRAGGING（1）表示用户手指“按在屏幕上并且开始拖动”的状态
        //SCROLL_STATE_SETTLING（2）在“手指离开屏幕”的状态
        //三种状态的出发顺序为（1，2，0）
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    //设置viewPager的适配器
    //MyAdpter是内部类
    class MyAdapter extends PagerAdapter {

        //获取总数
        @Override
        public int getCount() {
           if (contentView!=null){
               return contentView.size();
           }
           return 0;
        }
        //用于确认instantiateItem是否返回了和关键对象有关的Page视图
        @Override
        public boolean isViewFromObject(View view,Object object) {
            return view==object;
        }

        //实例化
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
             container.addView(contentView.get(position));
             return contentView.get(position);
        }

        //销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
             container.removeView(contentView.get(position));
        }
    }
}