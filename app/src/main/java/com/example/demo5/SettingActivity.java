package com.example.demo5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private RelativeLayout rl_modify_psw;
    private RelativeLayout rl_security_setting;
    private RelativeLayout rl_exit_login;

    public static SettingActivity instance=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        instance=this;
        initView();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("设置");

        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        title_bar.setBackgroundColor(Color.parseColor("#30b4ff"));

        //修改密码点击事件
        rl_modify_psw = (RelativeLayout) findViewById(R.id.rl_modify_psw);
        rl_modify_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到修改密码界面
                Intent intent = new Intent(SettingActivity.this,ModifyPswActivity.class);
                startActivity(intent);
            }
        });

        //设置密保点击事件
        rl_security_setting = (RelativeLayout) findViewById(R.id.rl_security_setting);
        rl_security_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到设置密保界面
                Intent intent = new Intent(SettingActivity.this,FindPswActivity.class);
                intent.putExtra("from","security");
                startActivity(intent);
            }
        });

        //退出登录的点击事件
        rl_exit_login = (RelativeLayout) findViewById(R.id.rl_exit_login);
        rl_exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "退出登录成功", Toast.LENGTH_SHORT).show();
                //清除SharedPreferences中的登陆状态和登录时的用户名
                clearLoginStatus();
                //退出登录成功后把退出成功的状态传递到MainActivity中
                Intent data = new Intent();
                data.putExtra("isLogin",false);
                setResult(RESULT_OK,data);
                SettingActivity.this.finish();
            }
        });
    }

    //清除SharedPreferences中的登陆状态和登录时的用户名
    private void clearLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("loginUserName","");
        editor.commit();
    }
}