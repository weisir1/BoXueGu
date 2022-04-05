package com.example.demo5.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo5.LoginActivity;
import com.example.demo5.R;
import com.example.demo5.SettingActivity;
import com.example.demo5.UserInfoActivity;
import com.example.demo5.Utils.AnalysisUtils;

public class MyInfoView {
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;

    private ImageView iv_head_icon;
    private TextView tv_user_name;
    private LinearLayout ll_head;
    private ImageView iv_course_historyicon;
    private RelativeLayout rl_course_history;
    private ImageView iv_userinfo_icon;
    private RelativeLayout rl_setting;

    public MyInfoView(Activity context) {
        mContext = context;
        //为之后Layout转化为view时用
        mInflater = LayoutInflater.from(mContext);
    }

    private void createView() {
        initView();
    }

    private void initView() {
        mCurrentView = mInflater.inflate(R.layout.mian_view_myinfo, null);
        iv_head_icon = (ImageView) mCurrentView.findViewById(R.id.iv_head_icon);
        tv_user_name = (TextView) mCurrentView.findViewById(R.id.tv_user_name);
        ll_head = (LinearLayout) mCurrentView.findViewById(R.id.ll_head);
//        iv_course_historyicon = (ImageView) mCurrentView.findViewById(R.id.iv_course_historyicon);
        rl_course_history = (RelativeLayout) mCurrentView.findViewById(R.id.rl_course_history);
//        iv_userinfo_icon = (ImageView) mCurrentView.findViewById(R.id.iv_userinfo_icon);
        rl_setting = (RelativeLayout) mCurrentView.findViewById(R.id.rl_setting);

        mCurrentView.setVisibility(View.VISIBLE);
        setLoginParams(readLoginStatus());

        ll_head.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //判断是否已经登陆
                if(readLoginStatus()){
                    //已经登陆跳转到个人资料界面
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivityForResult(intent,1);
                }
            }
        });
        rl_course_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){
                    //跳转到播放记录界面
                }else{
                    Toast.makeText(mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readLoginStatus()){
                    //跳转到设置界面
                    Intent intent = new Intent(mContext, SettingActivity.class);
                    mContext.startActivityForResult(intent,1);
                }else{
                    Toast.makeText(mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    //登陆成功后设置我的界面
    public void setLoginParams(boolean isLogin){
        if(isLogin){
            tv_user_name.setText(AnalysisUtils.readLoginUserName(mContext));
        }else{
            tv_user_name.setText("点击登录");
        }
    }

    //获取当前在导航栏上方显示对应的View
    public View getView(){
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }

    //显示当前导航栏上方对应的view界面
    public void showView(){
        if(mCurrentView==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }

    //从SharedPreferences中读取登陆的状态
    private boolean readLoginStatus() {
        SharedPreferences sp = mContext.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin",false);
        return isLogin;
    }
}
