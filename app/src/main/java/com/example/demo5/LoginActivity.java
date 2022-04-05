package com.example.demo5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo5.Utils.MD5Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private ImageView iv_head;
    private EditText et_user_name;
    private EditText et_psw;
    private Button btn_login;
    private TextView tv_register;
    private TextView tv_find_psw;

    private String userName,psw,spPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_psw = (EditText) findViewById(R.id.et_psw);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        tv_find_psw = (TextView) findViewById(R.id.tv_find_psw);
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面
                Intent intent = new Intent(LoginActivity.this,FindPswActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
                break;
        }
    }

    private void submit() {
        // validate

        userName = et_user_name.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        psw = et_psw.getText().toString().trim();
        String md5Psw = MD5Utils.md5(psw);
        spPsw=readPsw(userName);
        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if(md5Psw.equals(spPsw)){
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            //保存登陆状态和登录的用户名
            saveLoginStatus(true,userName);

            Intent data = new Intent();
            data.putExtra("isLogin",true);
            setResult(RESULT_OK,data);
            LoginActivity.this.finish();
            return;
        }

        if(!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw)){
            Toast.makeText(this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Toast.makeText(this, "此用户名不存在", Toast.LENGTH_SHORT).show();
        }
        // TODO validate success, do something


    }

    /*保存登陆状态和登陆用户名到SharedPreferences中*/
    private void saveLoginStatus(boolean status, String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        //获取编辑器
        editor.putBoolean("isLogin",status);
        //存入登陆时的用户名
        editor.putString("loginUserName",userName);
        editor.commit();
    }

    /*从SharePreferences中根据用户名读取密码*/
    private String readPsw(String userName) {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //从注册页面传过来的用户名
            String userName = data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                et_user_name.setSelection(userName.length());
            }
        }
    }
}