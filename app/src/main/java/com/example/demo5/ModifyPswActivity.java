package com.example.demo5;

import android.content.Intent;
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

import com.example.demo5.Utils.MD5Utils;

public class ModifyPswActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private EditText et_original_psw;
    private EditText et_new_psw;
    private EditText et_new_psw_again;
    private Button btn_save;

    private String originalPsw,newPsw,newPswAgain;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPswActivity.this.finish();
            }
        });
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("修改密码");
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        et_original_psw = (EditText) findViewById(R.id.et_original_psw);
        et_new_psw = (EditText) findViewById(R.id.et_new_psw);
        et_new_psw_again = (EditText) findViewById(R.id.et_new_psw_again);
        btn_save = (Button) findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:

                break;
        }
    }

    private void submit() {
        // validate
        originalPsw = et_original_psw.getText().toString().trim();
        if (TextUtils.isEmpty(originalPsw)) {
            Toast.makeText(this, "请输入原始密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!MD5Utils.md5(originalPsw).equals(readPsw())){
            Toast.makeText(this, "输入的密码与原始密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if(MD5Utils.md5(newPsw).equals(readPsw())){
            Toast.makeText(this, "输入的新密码与原始密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        newPsw = et_new_psw.getText().toString().trim();
        if (TextUtils.isEmpty(newPsw)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        newPswAgain = et_new_psw_again.getText().toString().trim();
        if (TextUtils.isEmpty(newPswAgain)) {
            Toast.makeText(this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newPsw.equals(newPswAgain)){
            Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "新密码设置成功", Toast.LENGTH_SHORT).show();
            //修改登录成功时保存在SharedPreferences中的密码
            modifyPsw(newPsw);

            Intent intent = new Intent(ModifyPswActivity.this,LoginActivity.class);
            startActivity(intent);
            SettingActivity.instance.finish();
            ModifyPswActivity.this.finish();
        }

        // TODO validate success, do something


    }

    //修改登录成功时保存在SharedPreferences中的密码
    private void modifyPsw(String newPsw) {
        String md5Psw = MD5Utils.md5(newPsw);
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
    }

    //从SharedPreferences中读取原始密码
    private String readPsw() {
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw = sp.getString(userName,"");
        return spPsw;
    }
}