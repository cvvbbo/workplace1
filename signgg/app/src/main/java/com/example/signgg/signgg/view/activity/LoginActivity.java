package com.example.signgg.signgg.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.signgg.signgg.Application.MyApplication;
import com.example.signgg.signgg.R;
import com.example.signgg.signgg.net.bean.Loginbeann;
import com.example.signgg.signgg.net.bean.Smsbean;
import com.example.signgg.signgg.net.httphelper;
import com.example.signgg.signgg.utils.Constant;
import com.example.signgg.signgg.utils.GsonUtil;
import com.example.signgg.signgg.utils.LogUtil;
import com.example.signgg.signgg.utils.Netutils;
import com.example.signgg.signgg.utils.NetworkUtils;
import com.example.signgg.signgg.utils.SignUtils;
import com.example.signgg.signgg.utils.Test3Builder;
import com.example.signgg.signgg.utils.ToastUtil;
import com.example.signgg.signgg.utils.sharepreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiong on 2017/8/15.
 */

public class LoginActivity extends AppCompatActivity {

    private static final int UN_CHECK = 100;
    private static final int CAN_CHECK = 200;
    @BindView(R.id.tb)
    Toolbar tb;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.passwordload)
    TextView passwordload;
    @BindView(R.id.phonenum)
    EditText phonenum;
    @BindView(R.id.send_code)
    Button sendCode;
    @BindView(R.id.signnum)
    EditText signnum;

    private  int time=60;


    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UN_CHECK:
                    time--;
                    sendCode.setText("请稍后"+time+"s");
                    break;
                case  CAN_CHECK:
                    time=60;
                    sendCode.setText("重新获取");
                    sendCode.setEnabled(true);
                    break;
            }
        }
    };





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        ButterKnife.bind(this);
        inintToolbar();
        sharepreferenceUtils.saveBooleandata(LoginActivity.this, "isfrist", false);




        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Netutils.isNetworkAvalible(MyApplication.mcontext)){
                    LogUtil.e("当前网络可用");
                    Logininto();
                }else {
                    LogUtil.e("当前网络不可用");
                    ToastUtil.showToast("当前网络不可用");
                   Netutils.checkNetwork(LoginActivity.this);
                }


            }
        });



        //账号密码登录
        passwordload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PasswloginActivity.class);
                startActivity(intent);
                //登录状态的保存还是在登录以后，不管是用什么登录的
                //这里以后也是要判断界面的sp存储的
            }
        });

        //发送验证码
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Getsignnum();

                if(Netutils.isNetworkAvalible(MyApplication.mcontext)){
                    LogUtil.e("当前网络可用");
                    Getsignnum();
                }else {
                    LogUtil.e("当前网络不可用");
                    ToastUtil.showToast("当前网络不可用");
                    Netutils.checkNetwork(LoginActivity.this);
                }
            }
        });
    }


    private void inintToolbar() {
        //这个是登录的toolbar
        setSupportActionBar(tb);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle("秒签小哥");

        //这个通用，自定义的还是非自定义的都是通用的。
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    //验证短信验证码，用于登录
    public void Logininto(){


        String codenum = signnum.getText().toString().trim();
        if (TextUtils.isEmpty(codenum)){
            ToastUtil.showToast("验证码不能空");

        }else {
             String phone = phonenum.getText().toString().trim();
            LogUtil.e("能获取到手机号么" + phone);
            LogUtil.e("验证码" + codenum);
            //传json
            String s = SignUtils.getsmsSign(phone, codenum);
            //验证
            String mylord = Test3Builder.Mylord(s);
            LogUtil.e("--------------------" + mylord);

            httphelper.create().dopost(Constant.SIGN_CHECK_URL, mylord, new httphelper.httpcallback() {
                @Override
                public void success(String s) {
                    Loginbeann loginbeann = GsonUtil.parseJsonToBean(s, Loginbeann.class);
                    int status = loginbeann.getStatus();
                    String msg = loginbeann.getMsg();
                    //添加入集合储存起来。
                    if (status == 0) {
                        String phone = phonenum.getText().toString().trim();
                        sharepreferenceUtils.saveStringdata(LoginActivity.this,"getphone",phone);
                        Intent intent = new Intent(LoginActivity.this, CheckidentityActivity.class);
                        startActivity(intent);
                        sharepreferenceUtils.saveBooleandata(LoginActivity.this, "islogin", false);
                        finish();
                        LogUtil.e("登录成功");

                    } else if (status == 1) {
                        ToastUtil.showToast(msg);
                        LogUtil.e(s);
                    }

                }

                @Override
                public void fail(Exception e) {
                    //这里以后再写一个类似于3种状态登录的
                    LogUtil.e(e + " ");

                }
            });
        }

    }

    //获取验证码
    public void Getsignnum() {

        //判断为不为空，手机号是不是正确的。。以后再考虑

        String phone = phonenum.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            ToastUtil.showToast("号码不能为空");

        }else {

            String s = SignUtils.sendsmsSign(phone);

            LogUtil.e("得到的字符串" + s);
            LogUtil.e("原来的字符串" + Constant.BASE_SEND_MSG);
            LogUtil.e("");

            String mylord = Test3Builder.Mylord(s);
            LogUtil.e(mylord);

            httphelper.create().dopost(Constant.BASE_SEND_URL, mylord, new httphelper.httpcallback() {
                @Override
                public void success(String s) {
                    Smsbean smsbean = GsonUtil.parseJsonToBean(s, Smsbean.class);
                    String msg = smsbean.getMsg();
                    int status = smsbean.getStatus();
                    if (status == 2) {
                        ToastUtil.showToast(msg + " ,每日短信次数用尽");
                    } else if (status == 0) {

                        //设置倒计时
                        sendCode.setEnabled(false);
                        new Thread() {
                            @Override
                            public void run() {
                                while (time > 1) {
                                    SystemClock.sleep(1000);
                                    h.sendEmptyMessage(UN_CHECK);
                                }
                                h.sendEmptyMessage(CAN_CHECK);

                            }
                        }.start();


                        ToastUtil.showToast(msg);


                    }
                    LogUtil.e(s);

                }

                @Override
                public void fail(Exception e) {
                    LogUtil.e(e + " ");

                }
            });
        }

    }
}
