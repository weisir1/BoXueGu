package com.example.demo5;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo5.Utils.AnalysisUtils;
import com.example.demo5.Utils.MD5Utils;

public class FindPswActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private TextView tv_user_name;
    private TextView tv_reset_psw;
    private EditText et_user_name;
    private EditText et_validate_name;
    private Button btn_validate;

    //from为security时从设置密保界面跳转过来的，否则就是从登录界面跳转过来的
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //获取从登陆界面和设置界面传递过来的数据
        from = getIntent().getStringExtra("from");
        initView();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindPswActivity.this.finish();
            }
        });

        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        if("security".equals(from)){
            tv_main_title.setText("设置密保");
        }else{
            tv_main_title.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }

        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_reset_psw = (TextView) findViewById(R.id.tv_reset_psw);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_validate_name = (EditText) findViewById(R.id.et_validate_name);
        btn_validate = (Button) findViewById(R.id.btn_validate);

        btn_validate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_validate:
                submit();
                break;
        }
    }

    private void submit() {
        String validateName = et_validate_name.getText().toString().trim();
        //设置密保
        if("security".equals(from)){
            if (TextUtils.isEmpty(validateName)) {
                Toast.makeText(this, "请输入要验证的姓名", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Toast.makeText(this, "密保设置成功", Toast.LENGTH_SHORT).show();
                //保存密保到SharePreferences中
                saveSecurity(validateName);
                FindPswActivity.this.finish();
            }

        }
        // validate
        String userName = et_user_name.getText().toString().trim();
        String sp_security = readSecurity(userName);
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "请输入您的用户名", Toast.LENGTH_SHORT).show();
            return;
        }else if(!isExistUserName(userName)){
            Toast.makeText(this, "您输入的用户名不存在", Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(validateName)){
            Toast.makeText(this, "请输入您要验证的姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!validateName.equals(sp_security)){
            Toast.makeText(this, "输入的密保不正确", Toast.LENGTH_SHORT).show();
            return;
        }else{
            //输入的密保正确，重新给用户设置一个密码
            tv_reset_psw.setVisibility(View.VISIBLE);
            tv_reset_psw.setText("初始的密码：123456");
            savePsw(userName);
        }



        // TODO validate success, do something


    }

    //保存初始化的密码
    private void savePsw(String userName) {
        String md5Psw = MD5Utils.md5("123456");
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
    }

    //从SharedPreferences中根据用户输入的用户名来判断是否由此用户
    private boolean isExistUserName(String userName) {
        boolean hasUserName = false;
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw = sp.getString(userName,"");
        if(!TextUtils.isEmpty(spPsw)){
            hasUserName=true;
        }
        return hasUserName;
    }

    //从SharedPreferences中读取密保
    private String readSecurity(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String security=sp.getString(userName+"_security","");
        return security;
    }

    //保存密保到SharePreferences中
    private void saveSecurity(String validateName) {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(AnalysisUtils.readLoginUserName(this)+"_security",validateName);
        editor.commit();
    }
}