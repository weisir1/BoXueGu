package com.example.demo5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo5.Utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private EditText et_user_name;
    private EditText et_psw;
    private EditText et_psw_again;
    private Button btn_register;
    private String userName,psw,pswAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        title_bar.setBackgroundColor(Color.TRANSPARENT);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_psw_again = (EditText) findViewById(R.id.et_psw_again);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
                break;
        }
    }

    /*
    * 获取控件中的字符串
    private void getEditString() {
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }*/

    private void submit() {
        // validate
        userName = et_user_name.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        psw = et_psw.getText().toString().trim();
        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        pswAgain = et_psw_again.getText().toString().trim();
        if (TextUtils.isEmpty(pswAgain)) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!psw.equals(pswAgain)){
            Toast.makeText(this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isExistUserName(userName)){
            Toast.makeText(this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            //把用户名和密码保存到SharedPreferences中
            saveRegisterInfo(userName,psw);
            //注册成功后把用户名传递到LoginActivity.java中
            Intent data = new Intent();
            data.putExtra("userName",userName);
            setResult(RESULT_OK,data);
            RegisterActivity.this.finish();
        }
        // TODO validate success, do something

    }

    /*从SharePreferences中读取输入的用户名，并判断此用户名是否存在*/
    private boolean isExistUserName (String userName){
        boolean has_userName=false;
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw = sp.getString(userName,"");
        if(!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return has_userName;
    }
    /*保存用户名和密码到SharedPreferences中*/
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.md5(psw);
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
    }
}