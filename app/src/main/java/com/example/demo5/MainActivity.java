package com.example.demo5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo5.view.ExercisesView;
import com.example.demo5.view.MyInfoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private ExercisesView mExercisesView;

    //中间内容栏
    private FrameLayout mBodyLayout;


    private TextView tv_course;
    private ImageView iv_course;
    private RelativeLayout mCourseBtn;
    private TextView tv_exercises;
    private ImageView iv_exercises;
    private RelativeLayout mExercisesBtn;
    private TextView tv_myinfo;
    private ImageView iv_myInfo;
    private RelativeLayout mMyInfoBtn;

    //底部按钮栏
    private LinearLayout mBottomLayout;

    private MyInfoView mMyInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();

        //设置底部三个按钮的点击监听事件
        setListener();

        //设置界面view的初始化状态
        setInitStatus();
    }



    //设置界面view的初始化状态
    private void setInitStatus() {
        clearBottomImageState();
        setSelectedStatus(0);
        createView(0);
    }


    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setVisibility(View.GONE);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("博学谷课程");
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));

        mBodyLayout = (FrameLayout) findViewById(R.id.main_body);
        tv_course = (TextView) findViewById(R.id.bottom_bar_text_course);
        iv_course = (ImageView) findViewById(R.id.bottom_bar_image_course);
        mCourseBtn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        tv_exercises = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        iv_exercises = (ImageView) findViewById(R.id.bottom_bar_image_exercises);
        mExercisesBtn = (RelativeLayout) findViewById(R.id.bottom_bar_exercises_btn);
        tv_myinfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);
        iv_myInfo = (ImageView) findViewById(R.id.bottom_bar_image_myinfo);
        mMyInfoBtn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);

        mBottomLayout = (LinearLayout) findViewById(R.id.main_bottom_bar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //课程的点击事件
            case R.id.bottom_bar_course_btn:
                //清除底部按钮的选中状态
                clearBottomImageState();
                //显示对应的页面
                selectDisplayView(0);
                break;
            //习题的点击事件
            case R.id.bottom_bar_exercises_btn:
                clearBottomImageState();
                selectDisplayView(1);
                break;
            //我的点击事件
            case R.id.bottom_bar_myinfo_btn:
                clearBottomImageState();
                selectDisplayView(2);
                break;
        }
    }

    //设置底部三个按钮的点击监听事件
    private void setListener() {
        //报空
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    //显示对应的页面
    private void selectDisplayView(int i) {
        //移除不需要的视图
        removeAllView();
        //选择视图
        createView(i);
        setSelectedStatus(i);
    }

    //选择视图
    private void createView(int viewi) {
        switch (viewi) {
            case 0:
                //课程界面
                break;
            case 1:
                //习题界面
                if(mExercisesView==null){
                    mExercisesView=new ExercisesView(this);
                    mBodyLayout.addView(mExercisesView.getView());
                }else{
                    mExercisesView.getView();
                }
                mExercisesView.showView();
                break;
            case 2:
                //我的界面
                if(mMyInfoView==null){
                    mMyInfoView = new MyInfoView(this);
                    mBodyLayout.addView(mMyInfoView.getView());
                }else{
                    mMyInfoView.getView();
                }
                mMyInfoView.showView();
                break;

        }
    }

    //清除底部按钮的选中状态
    private void clearBottomImageState() {
        tv_course.setTextColor(Color.parseColor("#666666"));
        tv_exercises.setTextColor(Color.parseColor("#666666"));
        tv_myinfo.setTextColor(Color.parseColor("#666666"));
        iv_course.setImageResource(R.drawable.main_course_icon);
        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
        iv_myInfo.setImageResource(R.drawable.main_my_icon);

        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            mBottomLayout.getChildAt(i).setSelected(false);
        }
    }

    private void setSelectedStatus(int i) {
        switch (i) {
            case 0:
                mCourseBtn.setSelected(true);
                iv_course.setImageResource(R.drawable.main_course_icon_selected);
                tv_course.setTextColor(Color.parseColor("#0097f7"));
                title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷课程");
                break;
            case 1:
                mExercisesBtn.setSelected(true);
                iv_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                tv_exercises.setTextColor(Color.parseColor("#0097f7"));
                title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("博学谷习题");
                break;
            case 2:
                mMyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.main_my_icon_selected);
                tv_myinfo.setTextColor(Color.parseColor("#0097f7"));
                title_bar.setVisibility(View.GONE);
                break;
        }
    }

    //移除不需要的视图
    private void removeAllView(){
        for (int i = 0; i < mBodyLayout.getChildCount(); i++) {
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    protected long exitTime;// 记录第一次的点击时的时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(this, "再按一次退出博学谷", Toast.LENGTH_SHORT).show();
                exitTime=System.currentTimeMillis();
            }else{
                MainActivity.this.finish();
                if(readLoginStatus()){
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取SharedPreferences中的登录状态
    private boolean readLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }

    //清除SharedPreferences中的登陆状态
    private void clearLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);//清楚登陆状态
        editor.putString("loginUserName","");//清除登陆时的用户名
        editor.commit();
    }


    //重写onActivityResult()方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            boolean isLogin = data.getBooleanExtra("isLogin",false);
            if(isLogin){
                //清除底部按钮的选中状态
                clearBottomImageState();
                //显示对应的页面
                selectDisplayView(0);
            }
            if(mMyInfoView!=null){
                //登陆成功或退出登录时根据isLogin设置我的界面
                mMyInfoView.setLoginParams(isLogin);
            }
        }
    }
}