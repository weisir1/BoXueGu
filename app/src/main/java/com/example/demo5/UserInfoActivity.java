package com.example.demo5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo5.Utils.AnalysisUtils;
import com.example.demo5.Utils.DBUtils;
import com.example.demo5.bean.UserBean;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_back;
    private TextView tv_main_title;
    private RelativeLayout title_bar;
    private ImageView iv_head_icon;
    private RelativeLayout rl_head;
    private TextView tv_user_name;
    private RelativeLayout rl_account;
    private TextView tv_nickName;
    private RelativeLayout rl_nickName;
    private TextView tv_sex;
    private RelativeLayout rl_sex;
    private TextView tv_signature;
    private RelativeLayout rl_signature;

    private String spUserName;
    private static final int CHANGE_NICKNAME=1;//修改昵称的自定义常量
    private static final int CHANGE_SIGNATURE=2;//修改签名的自定义常量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        spUserName= AnalysisUtils.readLoginUserName(this);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        iv_head_icon = (ImageView) findViewById(R.id.iv_head_icon);
        rl_head = (RelativeLayout) findViewById(R.id.rl_head);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        rl_account = (RelativeLayout) findViewById(R.id.rl_account);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickName);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_signature = (TextView) findViewById(R.id.tv_signature);
        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);
    }

    private void initData() {
        UserBean bean = null;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        //首先判断一下数据库里是否有数据
        if(bean==null){
            bean =new UserBean();
            bean.userName=spUserName;
            bean.nickName="问答精灵";
            bean.sex="男";
            bean.signature="问答精灵";
            //保存用户信息到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }

    //为界面控制设置值
    private void setValue(UserBean bean) {
        tv_nickName.setText(bean.nickName);
        tv_user_name.setText(bean.userName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
    }

    //设置控件的点击监听事件
    private void setListener() {
        tv_back.setOnClickListener(this);
        rl_nickName.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.rl_nickName:
                //昵称的点击事件
                String name = tv_nickName.getText().toString();//获取昵称控件上的数据
                Bundle bdName = new Bundle();
                bdName.putString("content",name);//传递界面上的昵称数据
                bdName.putString("title","昵称");
                bdName.putInt("flag",1);//flag为1时表示时修改昵称
                enterActivityForResult(ChangeUserInfo.class,CHANGE_NICKNAME,bdName);//跳转到个人资料修改界面
                break;
            case R.id.rl_sex:
                String sex=tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_signature:
                //签名的点击事件
                String signature = tv_signature.getText().toString();//获取签名控件上的数据
                Bundle bdSignature= new Bundle();
                bdSignature.putString("content",signature);//传递界面上的昵称数据
                bdSignature.putString("title","签名");
                bdSignature.putInt("flag",2);//flag为2时表示修改签名
                enterActivityForResult(ChangeUserInfo.class,CHANGE_NICKNAME,bdSignature);
                break;
            default:
                break;
        }
    }

    private void sexDialog(String sex) {
        int sexFlag=0;
        if("男".equals(sex)){
            sexFlag=0;
        }else if("女".equals(sex)){
            sexFlag=1;
        }
        final String items[]={"男","女"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("性别");
        builder.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this,items[which],
                        Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }
        });
        builder.create().show();
    }

    //更新界面上的性别数据
    private void setSex(String sex) {
        tv_sex.setText(sex);
        DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("sex",sex,spUserName);
    }

    /*
    * 获取回传数据时需要的跳转方法，第一个参数to表示需要跳转到的界面，
    * 第2个参数requestCode表示一个请求码，第3个参数b表示跳转时传递的数据
    */
    public void enterActivityForResult(Class<?>to,int requestCode,Bundle b){
        Intent i = new Intent(this,to);
        i.putExtras(b);
        startActivityForResult(i,requestCode);
    }

    private String new_info;//最新数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //个人资料修改界面回传过来的昵称数据
            case CHANGE_NICKNAME:
                new_info= data.getStringExtra("nickName");
                if(TextUtils.isEmpty(new_info)){
                    return;
                }
                tv_nickName.setText(new_info);
                //更新数据库中的昵称字段
                DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName",new_info,spUserName);
                break;
                //个人资料修改界面回传过来的签名数据
            case CHANGE_SIGNATURE:
                if(data!=null){
                    new_info=data.getStringExtra("signature");
                    if(TextUtils.isEmpty(new_info)){
                        return;
                    }
                    tv_signature.setText(new_info);
                    //更新数据库中签名字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature",new_info,spUserName);
                }
                break;
        }
    }
}